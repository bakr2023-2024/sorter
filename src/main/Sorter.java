package main;

public class Sorter {
    public int[] sort(int[] arr, SortingAlgs alg) {
        switch (alg) {
            case SELECTION_SORT:
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
    }
}
