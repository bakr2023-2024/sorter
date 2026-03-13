# Sorting Visualizer (Java)

A desktop **sorting algorithm visualizer** written in **Java** using **JavaFX**.  
It allows you to generate arrays, choose different sorting algorithms, and watch how the algorithms operate step-by-step.

The goal of this project is to make it easier to **understand and compare sorting algorithms visually**.

---

## Screenshot

![Sorting Visualizer GUI](assets/gui.png)

---

## Features

- Visualize many different sorting algorithms
- Adjustable **array size**
- Adjustable **delay/speed**
- Generate random arrays
- Start and stop sorting
- Real-time graphical updates
- Highlights active elements during comparisons

---

## Supported Sorting Algorithms

The visualizer currently supports the following algorithms:

### Simple Sorts
- Selection Sort
- Bubble Sort
- Insertion Sort
- Gnome Sort
- Cocktail Sort
- Odd-Even Sort
- Comb Sort

### Efficient Sorts
- Merge Sort
- Quick Sort
- Heap Sort
- Shell Sort
- Radix Sort

### Parallel / Exotic Sorts
- Bitonic Sort

### Fun / Unusual Sorts
- Pancake Sort
- Stooge Sort
- Bogo Sort

---

## Controls

| Control | Description |
|------|------|
| **Array Size** | Number of elements in the array |
| **Generate** | Generates a new random array |
| **Sorting Algorithms** | Select the algorithm to visualize |
| **Delay (ms)** | Delay between operations (controls animation speed) |
| **Sort** | Start sorting |
| **Stop** | Stop the current sorting process |

---

## How It Works

Each element in the array is represented as a **vertical bar** whose height corresponds to its value.

During sorting:
- Bars being compared are **highlighted**
- Swaps and movements are rendered in **real time**
- The visualization updates continuously until the array is sorted

---

## Project Structure
