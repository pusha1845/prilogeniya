package sasha.pkg5a;

import java.io.Serializable;  // ДОБАВИТЬ ЭТОТ ИМПОРТ

public class RecIntegral implements Serializable {  // ДОБАВИТЬ implements Serializable
    private static final long serialVersionUID = 1L;  // ДОБАВИТЬ ЭТУ СТРОКУ
    
    private static final double MIN_VALUE = 0.0000001;
    private static final double MAX_VALUE = 10000000;
    
    private double upperLim;      // верхний предел (highLimit)
    private double lowerLim;      // нижний предел (lowLimit)
    private double step;          // шаг интегрирования
    private double result;        // результат вычисления
    
    /**
     * Конструктор по умолчанию
     */
    public RecIntegral() {
        this.upperLim = 0.0;
        this.lowerLim = 0.0;
        this.step = 0.0;
        this.result = 0.0;
    }
    
    /**
     * Метод для проверки допустимого диапазона значений
     * @param value проверяемое значение
     * @param fieldName название поля для сообщения об ошибке
     * @throws InvalidRangeException если значение вне допустимого диапазона
     */
    private void validateRange(double value, String fieldName) throws InvalideRangeException {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new InvalideRangeException(
                    "Ошибка: " + fieldName + " должно быть в диапазоне от " + 
                    MIN_VALUE + " до " + MAX_VALUE + "\n", value
            );
        }
    }
    
    /**
     * Метод для проверки корректности всех параметров
     * @throws InvalidRangeException если какой-либо параметр вне допустимого диапазона
     * @throws IllegalArgumentException если нарушены логические условия интегрирования
     */
    private void validateParameters() throws InvalideRangeException, IllegalArgumentException {
        // Проверка диапазонов
        validateRange(lowerLim, "Нижний предел");
        validateRange(upperLim, "Верхний предел");
        validateRange(step, "Шаг интегрирования");
        
        // Проверка: нижний предел должен быть меньше верхнего
        if (lowerLim >= upperLim) {
            throw new IllegalArgumentException("Ошибка: нижний предел должен быть меньше верхнего предела");
        }
        
        // Проверка: шаг должен быть положительным
        if (step <= 0) {
            throw new IllegalArgumentException("Ошибка: шаг интегрирования должен быть положительным");
        }
        
        // Проверка: шаг не должен превышать интервал интегрирования
        if (step > (upperLim - lowerLim)) {
            throw new IllegalArgumentException("Ошибка: шаг интегрирования не может быть больше интервала интегрирования");
        }
    }
    
    /**
     * Конструктор с параметрами (только пределы и шаг)
     * @param lowerLim нижний предел интегрирования
     * @param upperLim верхний предел интегрирования
     * @param step шаг интегрирования
     * @throws IllegalArgumentException при ошибках ввода
     * @throws InvalideRangeException при выходе значений за допустимый диапазон
     */
    public RecIntegral(double lowerLim, double upperLim, double step) throws IllegalArgumentException, InvalideRangeException {
        this.lowerLim = lowerLim;
        this.upperLim = upperLim;
        this.step = step;
        this.result = 0.0;
        
        // Проверка всех параметров
        validateParameters();
    }
    
    /**
     * Конструктор с параметрами (пределы, шаг и результат)
     * @param lowerLim нижний предел интегрирования
     * @param upperLim верхний предел интегрирования
     * @param step шаг интегрирования
     * @param result результат вычисления
     * @throws IllegalArgumentException при ошибках ввода
     * @throws InvalidRangeException при выходе значений за допустимый диапазон
     */
    public RecIntegral(double lowerLim, double upperLim, double step, double result) throws IllegalArgumentException, InvalideRangeException {
        this.lowerLim = lowerLim;
        this.upperLim = upperLim;
        this.step = step;
        this.result = result;
        
        // Проверка всех параметров
        validateParameters();
    }
    
    /**
     * Метод вычисления интеграла методом трапеций для функции e^x/x
     * @return вычисленное значение интеграла
     * @throws InvalidRangeException если параметры не прошли валидацию
     */
    public double calculate() throws InvalideRangeException {
        // Проверяем параметры перед вычислением
        validateParameters();
        
        int n = (int)((upperLim - lowerLim) / step);
        double integral = 0;
        
        // Сумма средних точек (метод трапеций)
        for (int i = 1; i < n; i++) {
            double x = lowerLim + i * step;
            // Защита от деления на ноль (особая точка x=0)
            if (Math.abs(x) > 1e-10) {
                integral += Math.exp(x) / x;
            }
        }
        
        // Значения на границах
        double fa = (Math.abs(lowerLim) > 1e-10) ? Math.exp(lowerLim) / lowerLim : 0;
        double fb = (Math.abs(upperLim) > 1e-10) ? Math.exp(upperLim) / upperLim : 0;
        
        // Последняя полная точка
        double lastFullX = lowerLim + n * step;
        double fLast = (Math.abs(lastFullX) > 1e-10) ? Math.exp(lastFullX) / lastFullX : 0;
        
        // Остаток интервала
        double lastStep = upperLim - lastFullX;
        
        // Формула трапеций с учетом остатка
        if (Math.abs(lastStep) < 1e-10) {
            result = (step / 2) * (fa + 2 * integral + fLast);
        } else {
            result = (step / 2) * (fa + 2 * integral + fLast) + (lastStep / 2) * (fLast + fb);
        }
        
        return result;
    }
    
    /**
     * Перегруженный метод для вычисления интеграла с произвольными параметрами
     * @param lowerLim нижний предел
     * @param upperLim верхний предел
     * @param step шаг интегрирования
     * @return вычисленное значение интеграла
     * @throws InvalidRangeException если параметры не прошли валидацию
     */
    public double calculate(double lowerLim, double upperLim, double step) throws InvalideRangeException {
        // Временная проверка параметров
        if (lowerLim < MIN_VALUE || lowerLim > MAX_VALUE) {
            throw new InvalideRangeException("Нижний предел должен быть в диапазоне от " + MIN_VALUE + " до " + MAX_VALUE, lowerLim);
        }
        if (upperLim < MIN_VALUE || upperLim > MAX_VALUE) {
            throw new InvalideRangeException("Верхний предел должен быть в диапазоне от " + MIN_VALUE + " до " + MAX_VALUE, upperLim);
        }
        if (step < MIN_VALUE || step > MAX_VALUE) {
            throw new InvalideRangeException("Шаг должен быть в диапазоне от " + MIN_VALUE + " до " + MAX_VALUE, step);
        }
        
        // Проверка логических условий
        if (lowerLim >= upperLim) {
            throw new IllegalArgumentException("Ошибка: нижний предел должен быть меньше верхнего предела");
        }
        if (step <= 0) {
            throw new IllegalArgumentException("Ошибка: шаг интегрирования должен быть положительным");
        }
        if (step > (upperLim - lowerLim)) {
            throw new IllegalArgumentException("Ошибка: шаг интегрирования не может быть больше интервала интегрирования");
        }
        
        int n = (int)((upperLim - lowerLim) / step);
        double integral = 0;
        
        // Сумма средних точек (метод трапеций)
        for (int i = 1; i < n; i++) {
            double x = lowerLim + i * step;
            // Защита от деления на ноль (особая точка x=0)
            if (Math.abs(x) > 1e-10) {
                integral += Math.exp(x) / x;
            }
        }
        
        // Значения на границах
        double fa = (Math.abs(lowerLim) > 1e-10) ? Math.exp(lowerLim) / lowerLim : 0;
        double fb = (Math.abs(upperLim) > 1e-10) ? Math.exp(upperLim) / upperLim : 0;
        
        // Последняя полная точка
        double lastFullX = lowerLim + n * step;
        double fLast = (Math.abs(lastFullX) > 1e-10) ? Math.exp(lastFullX) / lastFullX : 0;
        
        // Остаток интервала
        double lastStep = upperLim - lastFullX;
        
        double tempResult;
        
        // Формула трапеций с учетом остатка
        if (Math.abs(lastStep) < 1e-10) {
            tempResult = (step / 2) * (fa + 2 * integral + fLast);
        } else {
            tempResult = (step / 2) * (fa + 2 * integral + fLast) + (lastStep / 2) * (fLast + fb);
        }
        
        return tempResult;
    }
    
    // Геттеры и сеттеры
    
    public double getUpperLim() {
        return upperLim;
    }
    
    public void setUpperLim(double upperLim) throws InvalideRangeException {
        validateRange(upperLim, "Верхний предел");
        this.upperLim = upperLim;
    }
    
    public double getLowerLim() {
        return lowerLim;
    }
    
    public void setLowerLim(double lowerLim) throws InvalideRangeException {
        validateRange(lowerLim, "Нижний предел");
        this.lowerLim = lowerLim;
    }
    
    public double getStep() {
        return step;
    }
    
    public void setStep(double step) throws InvalideRangeException {
        validateRange(step, "Шаг интегрирования");
        this.step = step;
    }
    
    public double getResult() {
        return result;
    }
    
    public void setResult(double result) {
        this.result = result;
    }
    
    /**
     * Альтернативные названия методов для совместимости
     */
    public double getLowLimit() {
        return lowerLim;
    }
    
    public void setLowLimit(double lowLimit) throws InvalideRangeException {
        validateRange(lowLimit, "Нижний предел");
        this.lowerLim = lowLimit;
    }
    
    public double getHighLimit() {
        return upperLim;
    }
    
    public void setHighLimit(double highLimit) throws InvalideRangeException {
        validateRange(highLimit, "Верхний предел");
        this.upperLim = highLimit;
    }
    
    @Override
    public String toString() {
        return String.format("lowerLimit=%.4f | upperLimit=%.4f | step=%.4f | result=%.6f", 
                             lowerLim, upperLim, step, result);
    }
}