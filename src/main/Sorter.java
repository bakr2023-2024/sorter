package main;

public class Sorter {
    public int[] sort(int[] arr, SortingAlgs alg) {
        switch (alg) {
            case SELECTION_SORT:
                selectionSort(arr);
                break;
            case BUBBLE_SORT:
                bubbleSort(arr);
            default:
                break;
        }
        return arr;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = Integer.MAX_VALUE;
            int idx = 0;
            for (int j = i; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    idx = j;
                }
            }
            if (idx != 0)
                swap(arr, i, idx);
        }
    }

    private void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            boolean swapped = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }
}
