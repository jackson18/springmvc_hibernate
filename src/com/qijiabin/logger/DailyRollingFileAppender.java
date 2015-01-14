package com.qijiabin.logger;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 功能：自定义日志备份适配�?
 * 
 * @author hc.zeng 2013-12-5
 */
public class DailyRollingFileAppender extends FileAppender
{
    private String datePattern = "'.'yyyy-MM-dd";
    private String scheduledFilename;
    private long nextCheck = System.currentTimeMillis() - 1L;
    private Date now = new Date();
    private SimpleDateFormat sdf;
    private RollingCalendar rc = new RollingCalendar();
    private static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
    protected long maxFileSize = 10485760L;
    protected int maxBackupIndex = 1;

    public DailyRollingFileAppender()
    {
    }

    public DailyRollingFileAppender(Layout layout, String filename,
            boolean append) throws IOException
    {
        super(layout, filename, append);
    }

    public DailyRollingFileAppender(Layout layout, String filename)
            throws IOException
    {
        super(layout, filename);
    }

    public int getMaxBackupIndex()
    {
        return this.maxBackupIndex;
    }

    public long getMaximumFileSize()
    {
        return this.maxFileSize;
    }

    public void setDatePattern(String pattern)
    {
        this.datePattern = pattern;
    }

    public String getDatePattern()
    {
        return this.datePattern;
    }
    public void setMaxBackupIndex(int maxBackups)
    {
        this.maxBackupIndex = maxBackups;
    }

    public void setMaximumFileSize(long maxFileSize)
    {
        this.maxFileSize = maxFileSize;
    }

    public void setMaxFileSize(String value)
    {
        this.maxFileSize = OptionConverter.toFileSize(value,
                this.maxFileSize + 1L);
    }

    protected void setQWForFiles(Writer writer)
    {
        this.qw = new CountingQuietWriter(writer, this.errorHandler);
    }
    
    public void activateOptions()
    {
        super.activateOptions();
        if ((this.datePattern != null) && (this.fileName != null))
        {
            this.now.setTime(System.currentTimeMillis());
            this.sdf = new SimpleDateFormat(this.datePattern);
            int type = computeCheckPeriod();
            this.rc.setType(type);
            File file = new File(this.fileName);
            this.scheduledFilename = this.fileName
                    + this.sdf.format(new Date(file.lastModified()));
        }
        else
        {
            LogLog.error("Either File or DatePattern options are not set for appender ["
                    + this.name + "].");
        }
    }

    public int computeCheckPeriod()
    {
        RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone,
                Locale.ENGLISH);

        Date epoch = new Date(0L);
        if (this.datePattern != null)
        {
            for (int i = 0; i <= 5; ++i)
            {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                        this.datePattern);
                simpleDateFormat.setTimeZone(gmtTimeZone);
                String r0 = simpleDateFormat.format(epoch);
                rollingCalendar.setType(i);
                Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
                String r1 = simpleDateFormat.format(next);

                if ((r0 != null) && (r1 != null) && (!(r0.equals(r1)))) { return i; }
            }
        }
        return -1;
    }

    /**
     * 功能：日志备份规则定制方�?
     * @author hc.zeng 2013-12-7
     * @param isNextDay
     * @param nextFile
     */
    public void rollOver(boolean isNextDay, boolean nextFile)
    {

        // 如果为换天了
        if (isNextDay)
        {
            String datedFilename = this.fileName + this.sdf.format(this.now);
            // 如果当前日志路径不等于新建的日志路径，则为换天，备份文件�?
            if (!this.scheduledFilename.equals(datedFilename))
            {
                //先关闭文�?
                super.closeFile();
                //当前文件对象
                File target = new File(this.scheduledFilename);
                if (target.exists())
                {
                    target.delete();
                }
                // 实际文件的对�?
                File file = new File(this.fileName);
                // 修改日志文件名称
                file.renameTo(target);
                try
                {
                    // 重设文件对象，生成新的日志文�?
                    super.setFile(this.fileName, false, this.bufferedIO,
                            this.bufferSize);
                }
                catch (IOException e)
                {
                    this.errorHandler.error("setFile(" + this.fileName + ", false) call failed.");
                }
                this.scheduledFilename = datedFilename;
            }
        }
        // 判断�?��长度有没有超过，如果超过则备份文件，生成新文�?
        if (nextFile)
        {
            if (this.maxBackupIndex > 0)
            {
                //判断�?��备份文件是否存在，如果存在则删除
                File file = new File(this.scheduledFilename + '.' + this.maxBackupIndex);
                if (file.exists())
                {
                    file.delete();
                }

                //将所有的备份文件向后推移1位（通过重命名的方式�?
                for (int i = this.maxBackupIndex - 1; i >= 1; --i)
                {
                    file = new File(this.scheduledFilename + "." + i);
                    if (file.exists())
                    {
                        File target = new File(this.scheduledFilename + '.'+ (i + 1));
                        file.renameTo(target);
                    }
                }

                // 新的备份文件
                File target = new File(this.scheduledFilename + "." + 1);
                super.closeFile();
                // 将正在使用的日志文件重命�?
                file = new File(this.fileName);
                file.renameTo(target);
            }
            try
            {
                // 设置新的文件对象
                super.setFile(this.fileName, false, this.bufferedIO,this.bufferSize);
            }
            catch (IOException e)
            {
                LogLog.error("setFile(" + this.fileName + ", false) call failed.", e);
            }
        }
    }

    /**
     *  追加文件
     */
    protected void subAppend(LoggingEvent event)
    {
        super.subAppend(event);
        boolean nextDay = nextDate();
        boolean nextFile = false;
        // 如果文件大小超过指定�?
        if ((this.fileName != null)
                && (((CountingQuietWriter) this.qw).getCount() > this.maxFileSize))
        {
            nextFile = true;
        }
        rollOver(nextDay, nextFile);
    }

    private boolean nextDate()
    {
        long n = System.currentTimeMillis();
        if (n >= this.nextCheck)
        {
            this.now.setTime(n);
            this.nextCheck = this.rc.getNextCheckMillis(this.now);
            return true;
        }
        return false;
    }
}
