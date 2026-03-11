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
            case SHELL_SORT:
                shellSort(arr);
            case COUNT_SORT:
                countSort(arr);
            case RADIX_SORT:
                radixSort(arr);
            case HEAP_SORT:
                heapSort(arr);
            case COMB_SORT:
                combSort(arr);
            case CYCLE_SORT:
                cycleSort(arr);
            case COCKTAIL_SORT:
                cocktailSort(arr);
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

    private int max(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i])
                max = arr[i];
        }
        return max;
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

    private void shellSort(int[] arr) {
        int gap = arr.length / 2;
        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int curr = arr[j];
                while ((j - gap) >= 0 && arr[j - gap] > curr) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = curr;
            }
            gap /= 2;
        }
    }

    private void countSort(int[] arr) {
        int[] freq = new int[max(arr) + 1];
        for (int i = 0; i < arr.length; i++)
            freq[arr[i]]++;
        for (int i = 1; i < freq.length; i++)
            freq[i] += freq[i - 1];
        int[] ans = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            int val = arr[i];
            ans[freq[val] - 1] = val;
            freq[val]--;
        }
        for (int i = 0; i < arr.length; i++)
            arr[i] = ans[i];
    }

    private void countSort(int[] arr, int exp) {
        int[] freq = new int[10];
        for (int i = 0; i < arr.length; i++)
            freq[(arr[i] / exp) % 10]++;
        for (int i = 1; i < freq.length; i++)
            freq[i] += freq[i - 1];
        int[] ans = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            int val = (arr[i] / exp) % 10;
            ans[freq[val] - 1] = arr[i];
            freq[val]--;
        }
        for (int i = 0; i < arr.length; i++)
            arr[i] = ans[i];
    }

    private void radixSort(int[] arr) {
        int max = max(arr);
        for (int exp = 1; max / exp > 0; exp *= 10)
            countSort(arr, exp);
    }

    private void heapify(int[] arr, int i, int n) {
        int largest = i;
        int l = i * 2 + 1, r = i * 2 + 2;
        if (l < n && arr[largest] < arr[l])
            largest = l;
        if (r < n && arr[largest] < arr[r])
            largest = r;
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, largest, n);
        }
    }

    private void heapSort(int[] arr) {
        for (int i = (arr.length / 2) - 1; i >= 0; i--)
            heapify(arr, i, arr.length);
        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapify(arr, 0, i);
        }
    }

    private void combSort(int[] arr) {
        int gap = arr.length;
        boolean swapped = false;
        do {
            gap = (int) (gap / 1.3);
            swapped = false;
            if (gap < 1)
                gap = 1;
            for (int i = 0; i + gap < arr.length; i++) {
                if (arr[i] > arr[i + gap]) {
                    swap(arr, i, i + gap);
                    swapped = true;
                }
            }
        } while (swapped);
    }

    private int cycle(int[] arr, int i) {
        int pos = i;
        for (int j = i + 1; j < arr.length; j++) {
            if (arr[i] > arr[j])
                pos++;
        }
        if (pos != i) {
            while (arr[i] == arr[pos])
                pos++;
            swap(arr, i, pos);
        }
        return pos;
    }

    private void cycleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int pos = i;
            do {
                pos = cycle(arr, i);
            } while (pos != i);
        }
    }

    private void cocktailSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            boolean swapped = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            for (int j = arr.length - 1 - i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }
}
