package com.qijiabin.logger;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class RollingCalendar extends GregorianCalendar
{

    /**
     * 
     */
    private static final long serialVersionUID = 6286423340768375456L;
    int type = -1;

    RollingCalendar()
    {
    }

    RollingCalendar(TimeZone tz, Locale locale)
    {
        super(tz, locale);
    }

    void setType(int type)
    {
        this.type = type;
    }

    public long getNextCheckMillis(Date now)
    {
        return getNextCheckDate(now).getTime();
    }

    public Date getNextCheckDate(Date now) {
      super.setTime(now);

      switch (this.type)
      {
      case 0:
        super.set(13, 0);
        super.set(14, 0);
        super.add(12, 1);
        break;
      case 1:
        super.set(12, 0);
        super.set(13, 0);
        super.set(14, 0);
        super.add(11, 1);
        break;
      case 2:
        super.set(12, 0);
        super.set(13, 0);
        super.set(14, 0);
        int hour = super.get(11);
        if (hour < 12) {
          super.set(11, 12); break ;
        }
        super.set(11, 0);
        super.add(5, 1);

        break;
      case 3:
        super.set(11, 0);
        super.set(12, 0);
        super.set(13, 0);
        super.set(14, 0);
        super.add(5, 1);
        break;
      case 4:
        super.set(7, super.getFirstDayOfWeek());
        super.set(11, 0);
        super.set(12, 0);
        super.set(13, 0);
        super.set(14, 0);
        super.add(3, 1);
        break;
      case 5:
        super.set(5, 1);
        super.set(11, 0);
        super.set(12, 0);
        super.set(13, 0);
        super.set(14, 0);
        super.add(2, 1);
        break;
      default:
        throw new IllegalStateException("Unknown periodicity type.");
      }
      return super.getTime();
    }
}
