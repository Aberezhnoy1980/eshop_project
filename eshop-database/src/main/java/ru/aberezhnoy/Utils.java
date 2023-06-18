package ru.aberezhnoy;

public class Utils {
    public static void main(String[] args) {
        // инициализируем тестовый массив
        int[] arr = {20, 40, 50, 22, 5, 1, 9, 4, 4, 15};
        int i = 0;
        int minValueIdx = 0;
        int maxValueIdx = 0;
        int sumBetweenMinMax = 0;
        int startIdx;
        int endIdx;

        // ищем индексы с минимальным и максимальным значениями
        while (i < arr.length) {
            if (arr[i] > arr[maxValueIdx]) {
                maxValueIdx = i;
            }
            if (arr[i] < arr[minValueIdx]) {
                minValueIdx = i;
            }
            i++;
        }

        // определяем начало и конец промежутка между экстремумами
        if (minValueIdx < maxValueIdx) {
            startIdx = minValueIdx;
            endIdx = maxValueIdx;
        } else {
            startIdx = maxValueIdx;
            endIdx = minValueIdx;
        }

        // подсчитываем сумму промежутка между экстремумами
        while (startIdx <= endIdx) {
            sumBetweenMinMax += arr[startIdx];
            startIdx++;
        }
        System.out.printf("min = %d, max = %d, sumBetweenMinMax = %d", minValueIdx, maxValueIdx, sumBetweenMinMax);

        int sumForAvg = 0;
        int[] arrForAvg = {1, 2, 3, 4, 5};
        // вместо while используем более уместный здесь for
        for (int j = 0; j < arrForAvg.length; j++) {
            sumForAvg += arrForAvg[j];
        }
        // результат не сохраняем, а просто выводим в консоль
        System.out.println("\n Average value is: " + sumForAvg / arrForAvg.length);
    }
}
