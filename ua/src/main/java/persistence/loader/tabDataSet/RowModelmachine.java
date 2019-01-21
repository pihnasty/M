package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

/**
 * Описание сущности строка таблицы: Модель машины (Модель оборудования)
 */
public class RowModelmachine extends RowIdNameDescription {

    /**
     * Инициализирует строку таблицы: Модель машины
     *
     * @param id	Id модели машины
     * @param name	Название модели машины
     * @param overallDimensionX	Габаритный X-размер машины в метрах
     * @param overallDimensionY	Габаритный Y-размер машины в метрах
     * @param workSizeX	X-размер производственной площади для машины,
     * необходимый для работы в метрах
     * @param workSizeY	Y-размер производственной площади для машины,
     * необходимый для работы в метрах
     * @param description	Описание машины
     */
    public RowModelmachine(int id, String name, String img, double overallDimensionX, double overallDimensionY, double workSizeX, double workSizeY, String description) {
        super(id, name, description);
        this.overallDimensionX = overallDimensionX;
        this.overallDimensionY = overallDimensionY;
        this.workSizeX = workSizeX;
        this.workSizeY = workSizeY;
        this.setImg(img);
    }

    public RowModelmachine() {
        super();
        this.setName("Пресс кривошипный К117");
    }

    public RowModelmachine(DataSet dataSet, Class cL) {
        super(dataSet, cL);
        this.setName("Пресс кривошипный К117");
        this.setImg(img);
    }

    /* Габаритный X-размер машины  в метрах																*/
    private double overallDimensionX = 1;
    /* Габаритный Y-размер машины  в метрах																*/
    private double overallDimensionY = 1;
    /* Габаритный X-размер производственной площади для машины, необходимый для работы  в метрах		*/
    private double workSizeX = 3;
    /* Габаритный Y-размер производственной площади для машины, необходимый для работы  в метрах		*/
    private double workSizeY = 3;
    /* Фото машины																						*/
    private String img = "Image/Machine/press_16.png";

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getOverallDimensionX() {
        return overallDimensionX;
    }

    public void setOverallDimensionX(double overallDimensionX) {
        this.overallDimensionX = overallDimensionX;
    }

    public double getOverallDimensionY() {
        return overallDimensionY;
    }

    public void setOverallDimensionY(double overallDimensionY) {
        this.overallDimensionY = overallDimensionY;
    }

    public double getWorkSizeX() {
        return workSizeX;
    }

    public void setWorkSizeX(double workSizeX) {
        this.workSizeX = workSizeX;
    }

    public double getWorkSizeY() {
        return workSizeY;
    }

    public void setWorkSizeY(double workSizeY) {
        this.workSizeY = workSizeY;
    }
}



// @param type			Тип машины	 (например: прессовое оборудование, термопласты, гильятины, пресса) 
