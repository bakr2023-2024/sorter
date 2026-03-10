package main;

public class Sorter {
    public int[] sort(int[] arr, SortingAlgs alg) {
        switch (alg) {
            case SELECTION_SORT:
                selectionSort(arr);
                break;
            case BUBBLE_SORT:
                bubbleSort(arr);
            case INSERTION_SORT:
                insertionSort(arr);
            case MERGE_SORT:
                mergeSort(arr, 0, arr.length - 1);
            case QUICK_SORT:
                quickSort(arr, 0, arr.length - 1);
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
            if (idx != i)
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

    private void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            int curr = arr[j];
            while (j > 0 && arr[j - 1] > curr) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = curr;
        }
    }

    private void merge(int[] arr, int s, int m, int e) {
        int l1 = m - s + 1;
        int l2 = e - m;
        int[] a = new int[l1];
        int[] b = new int[l2];
        for (int i = 0; i < l1; i++)
            a[i] = arr[s + i];
        for (int i = 0; i < l2; i++)
            b[i] = arr[m + i + 1];
        int i = 0, j = 0, k = s;
        while (i < l1 && j < l2) {
            if (a[i] <= b[j])
                arr[k++] = a[i++];
            else
                arr[k++] = b[j++];
        }
        while (i < l1)
            arr[k++] = a[i++];
        while (j < l2)
            arr[k++] = b[j++];
    }

    private void mergeSort(int[] arr, int s, int e) {
        if (s < e) {
            int m = (e - s) / 2 + s;
            mergeSort(arr, s, m);
            mergeSort(arr, m + 1, e);
            merge(arr, s, m, e);
        }
    }

    private int partition(int arr[], int s, int e) {
        int pivot = arr[e];
        int i = s - 1;
        for (int j = s; j < e; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, e, i + 1);
        return i + 1;
    }

    private void quickSort(int arr[], int s, int e) {
        if (s < e) {
            int p = partition(arr, s, e);
            quickSort(arr, s, p - 1);
            quickSort(arr, p + 1, e);
        }
    }
}
