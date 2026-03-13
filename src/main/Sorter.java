package main;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
public class Sorter {
    public volatile boolean stop = false;

    private TriConsumer onComp = (int[] a, int i, int j) -> {
    };
    private Consumer<int[]> onSwap = (int[] a) -> {
    };

    public Sorter(TriConsumer onComp, Consumer<int[]> onSwap) {
        if (onComp != null)
            this.onComp = onComp;
        if (onSwap != null)
            this.onSwap = onSwap;
    }
    public int[] sort(int[] arr, SortingAlgs alg) {
        switch (alg) {
            case SELECTION_SORT:
                selectionSort(arr);
                break;
            case BUBBLE_SORT:
                bubbleSort(arr);
                break;
            case INSERTION_SORT:
                insertionSort(arr);
                break;
            case MERGE_SORT:
                mergeSort(arr, 0, arr.length - 1);
                break;
            case QUICK_SORT:
                quickSort(arr, 0, arr.length - 1);
                break;
            case SHELL_SORT:
                shellSort(arr);
                break;
            case COUNT_SORT:
                countSort(arr);
                break;
            case RADIX_SORT:
                radixSort(arr);
                break;
            case HEAP_SORT:
                heapSort(arr);
                break;
            case COMB_SORT:
                combSort(arr);
                break;
            case CYCLE_SORT:
                cycleSort(arr);
                break;
            case COCKTAIL_SORT:
                cocktailSort(arr);
                break;
            case BITONIC_SORT:
                int n = arr.length;
                int len = (n & (n - 1)) == 0 ? n : Integer.highestOneBit(n) << 1;
                int[] newArr = Arrays.copyOf(arr, len);
                int max = max(arr);
                Arrays.fill(newArr, n, len, max);
                bitonicSort(newArr, 0, len, true);
                for (int i = 0; i < n; i++)
                    arr[i] = newArr[i];
                break;
            case PANCAKE_SORT:
                pancakeSort(arr);
                break;
            case STOOGE_SORT:
                stoogeSort(arr, 0, arr.length - 1);
                break;
            case ODDEVEN_SORT:
                oddEvenSort(arr);
                break;
            case GNOME_SORT:
                gnomeSort(arr);
                break;
            case BOGO_SORT:
                bogoSort(arr);
                break;
            default:
                break;
        }
        return arr;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        onSwap.accept(arr);
    }

    private int max(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i])
                max = arr[i];
        }
        return max;
    }

    private int maxIdx(int[] arr, int n) {
        int max = Integer.MIN_VALUE;
        int idx = 0;
        for (int i = 0; i < n; i++) {
            if (max < arr[i]) {
                max = arr[i];
                idx = i;
            }
        }
        return idx;
    }

    private void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length && !stop; i++) {
            int idx = i;
            for (int j = i + 1; j < arr.length && !stop; j++) {
                onComp.accept(arr, idx, j);
                if (arr[idx] > arr[j]) {
                    idx = j;
                }
            }
            if (idx != i) {
                swap(arr, i, idx);
            }
        }
    }

    private void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length && !stop; i++) {
            boolean swapped = false;
            for (int j = 0; j < arr.length - 1 - i && !stop; j++) {
                onComp.accept(arr, j, j + 1);
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
        for (int i = 1; i < arr.length && !stop; i++) {
            int j = i;
            while (j > 0 && arr[j - 1] > arr[j] && !stop) {
                onComp.accept(arr, j, j - 1);
                swap(arr, j, j - 1);
                onSwap.accept(arr);
                j--;
            }
        }
    }

    private void merge(int[] arr, int s, int m, int e) {
        int l1 = m - s + 1;
        int l2 = e - m;
        int[] a = new int[l1];
        int[] b = new int[l2];
        for (int i = 0; i < l1 && !stop; i++)
            a[i] = arr[s + i];
        for (int i = 0; i < l2 && !stop; i++)
            b[i] = arr[m + i + 1];
        int i = 0, j = 0, k = s;
        while (i < l1 && j < l2 && !stop) {
            onComp.accept(arr, s + i, m + j + 1);
            if (a[i] <= b[j])
                arr[k++] = a[i++];
            else
                arr[k++] = b[j++];
            onSwap.accept(arr);
        }
        while (i < l1 && !stop) {
            arr[k++] = a[i++];
            onSwap.accept(arr);
        }
        while (j < l2 && !stop) {
            arr[k++] = b[j++];
            onSwap.accept(arr);
        }
    }

    private void mergeSort(int[] arr, int s, int e) {
        if (s < e && !stop) {
            int m = (e - s) / 2 + s;
            mergeSort(arr, s, m);
            mergeSort(arr, m + 1, e);
            merge(arr, s, m, e);
        }
    }

    private int partition(int arr[], int s, int e) {
        int pivot = arr[e];
        int i = s - 1;
        for (int j = s; j < e && !stop; j++) {
            if (stop)
                return 0;
            onComp.accept(arr, e, j);
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, e, i + 1);
        return i + 1;
    }

    private void quickSort(int arr[], int s, int e) {
        if (s < e && !stop) {
            int p = partition(arr, s, e);
            quickSort(arr, s, p - 1);
            quickSort(arr, p + 1, e);
        }
    }

    private void shellSort(int[] arr) {
        int gap = arr.length / 2;
        while (gap > 0 && !stop) {
            for (int i = gap; i < arr.length && !stop; i++) {
                int j = i;
                int curr = arr[j];
                while ((j - gap) >= 0 && arr[j - gap] > curr && !stop) {
                    onComp.accept(arr, j, j - gap);
                    swap(arr, j, j - gap);
                    j -= gap;
                }
            }
            gap /= 2;
        }
    }

    private void countSort(int[] arr) {
        int[] freq = new int[max(arr) + 1];
        for (int i = 0; i < arr.length && !stop; i++)
            freq[arr[i]]++;
        for (int i = 1; i < freq.length && !stop; i++)
            freq[i] += freq[i - 1];
        int[] ans = new int[arr.length];
        for (int i = arr.length - 1; i >= 0 && !stop; i--) {
            int val = arr[i];
            ans[freq[val] - 1] = val;
            freq[val]--;
        }
        for (int i = 0; i < arr.length && !stop; i++) {
            arr[i] = ans[i];
            onSwap.accept(arr);
        }
    }

    private void countSort(int[] arr, int exp) {
        int[] freq = new int[10];
        for (int i = 0; i < arr.length && !stop; i++)
            freq[(arr[i] / exp) % 10]++;
        for (int i = 1; i < freq.length && !stop; i++)
            freq[i] += freq[i - 1];
        int[] ans = new int[arr.length];
        for (int i = arr.length - 1; i >= 0 && !stop; i--) {
            int val = (arr[i] / exp) % 10;
            ans[freq[val] - 1] = arr[i];
            freq[val]--;
        }
        for (int i = 0; i < arr.length && !stop; i++) {
            arr[i] = ans[i];
            onSwap.accept(arr);
        }
    }

    private void radixSort(int[] arr) {
        int max = max(arr);
        for (int exp = 1; max / exp > 0 && !stop; exp *= 10)
            countSort(arr, exp);
    }

    private void heapify(int[] arr, int i, int n) {
        if (stop)
            return;
        int largest = i;
        int l = i * 2 + 1, r = i * 2 + 2;
        if (l < n) {
            onComp.accept(arr, largest, l);
            if (arr[largest] < arr[l])
            largest = l;
    }
    if (r < n) {
        onComp.accept(arr, largest, r);
        if (arr[largest] < arr[r])
            largest = r;
    }
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, largest, n);
        }
    }

    private void heapSort(int[] arr) {
        for (int i = (arr.length / 2) - 1; i >= 0 && !stop; i--)
            heapify(arr, i, arr.length);
        for (int i = arr.length - 1; i >= 0 && !stop; i--) {
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
            for (int i = 0; i + gap < arr.length && !stop; i++) {
                onComp.accept(arr, i, i + gap);
                if (arr[i] > arr[i + gap]) {
                    swap(arr, i, i + gap);
                    swapped = true;
                }
            }
        } while (swapped && !stop);
    }

    private int cycle(int[] arr, int i) {
        int pos = i;
        for (int j = i + 1; j < arr.length && !stop; j++) {
            if (arr[i] > arr[j])
                pos++;
        }
        if (pos != i) {
            while (arr[i] == arr[pos] && !stop)
                pos++;
            swap(arr, i, pos);
        }
        return pos;
    }

    private void cycleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1 && !stop; i++) {
            int pos = i;
            do {
                pos = cycle(arr, i);
            } while (pos != i);
        }
    }

    private void cocktailSort(int[] arr) {
        for (int i = 0; i < arr.length && !stop; i++) {
            boolean swapped = false;
            for (int j = 0; j < arr.length - 1 - i && !stop; j++) {
                onComp.accept(arr, j, j + 1);
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            for (int j = arr.length - 1 - i; j > 0 && !stop; j--) {
                onComp.accept(arr, j, j - 1);
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }

    private void bitonicMerge(int[] arr, int start, int count, boolean asc) {
        if (count > 1 && !stop) {
            int k = count / 2;
            for (int i = start; i < start + k && !stop; i++) {
                onComp.accept(arr, i, i + k);
                if (asc == (arr[i] > arr[i + k]))
                    swap(arr, i, i + k);
            }
            bitonicMerge(arr, start, k, asc);
            bitonicMerge(arr, start + k, k, asc);
        }
    }

    private void bitonicSort(int[] arr, int start, int count, boolean asc) {
        if (count > 1 && !stop) {
            int k = count / 2;
            bitonicSort(arr, start, k, true);
            bitonicSort(arr, start + k, k, false);
            bitonicMerge(arr, start, count, asc);
        }
    }

    private void flip(int[] arr, int mi) {
        for (int i = 0, j = mi; i < j && !stop; i++, j--)
            swap(arr, i, j);
    }

    private void pancakeSort(int[] arr) {
        for (int size = arr.length; size > 1 && !stop; size--) {
            int idx = maxIdx(arr, size);
            onComp.accept(arr, idx, idx);
            flip(arr, idx);
            flip(arr, size - 1);
        }
    }

    private void stoogeSort(int[] arr, int start, int end) {
        if (stop)
            return;
        onComp.accept(arr, start, end);
        if (arr[start] > arr[end])
            swap(arr, start, end);
        if (end - start + 1 > 2) {
            int t = (int) Math.ceil((end - start + 1) / 3);
            stoogeSort(arr, start, end - t);
            stoogeSort(arr, start + t, end);
            stoogeSort(arr, start, end - t);
        }
    }

    private void oddEvenSort(int[] arr) {
        boolean swapped = false;
        do {
            swapped = false;
            for (int i = 0; i < arr.length - 1 && !stop; i += 2) {
                onComp.accept(arr, i, i + 1);
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    swapped = true;
                }
            }
            for (int i = 1; i < arr.length - 1 && !stop; i += 2) {
                onComp.accept(arr, i, i + 1);
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped && !stop);
    }

    private void gnomeSort(int[] arr) {
        int i = 0;
        while (i < arr.length && !stop) {
            if (i == 0) {
                i++;
                continue;
            }
            onComp.accept(arr, i, i - 1);
            if (arr[i] >= arr[i - 1]) {
                i++;
            } else {
                swap(arr, i, i - 1);
                i--;
            }

        }
    }

    private void bogoSort(int[] arr) {
        Random rand = new Random();
        boolean isSorted = false;
        do {
            for (int i = arr.length - 1; i >= 0 && !stop; i--) {
                int r = rand.nextInt(i + 1);
                swap(arr, i, r);
            }
            isSorted = true;
            for (int i = 0; i < arr.length - 1 && !stop; i++) {
                onComp.accept(arr, i, i + 1);
                if (arr[i] > arr[i + 1]) {
                    isSorted = false;
                    break;
                }
            }
        } while (!isSorted && !stop);
    }
}
