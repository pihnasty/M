package designpatterns;

import java.util.Date;

public interface ColumnDate {
    Date getDateBegin();
    void setDateBegin(Date date);
    void setDateEnd(Date date);
    Date getDateEnd();
}
