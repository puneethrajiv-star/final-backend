package com.anurag.ai.config;

import com.anurag.ai.entity.DsaProblem;
import com.anurag.ai.enums.Difficulty;
import com.anurag.ai.repository.DsaProblemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DsaProblemSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DsaProblemSeeder.class);
    private final DsaProblemRepository dsaProblemRepository;

    @Override
    public void run(String... args) {
        List<DsaProblem> problems = new ArrayList<>();

        // EASY Problems (8)
        problems.add(createProblem(
            "Two Sum",
            Difficulty.EASY,
            "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target. You may assume that each input would have exactly one solution.",
            "public class Solution {\n    public int[] twoSum(int[] nums, int target) {\n        // Write your solution here\n        return new int[0];\n    }\n}"
        ));
        problems.add(createProblem(
            "Reverse a String",
            Difficulty.EASY,
            "Given a string s, return the reversed string using a loop or two-pointer approach without using built-in reverse functions.",
            "public class Solution {\n    public String reverseString(String s) {\n        // Write your solution here\n        return \"\";\n    }\n}"
        ));
        problems.add(createProblem(
            "Find Maximum Element in Array",
            Difficulty.EASY,
            "Given an array of integers nums, find and return the maximum value in the array. Assume the array contains at least one element.",
            "public class Solution {\n    public int findMax(int[] nums) {\n        // Write your solution here\n        return 0;\n    }\n}"
        ));
        problems.add(createProblem(
            "Palindrome Check",
            Difficulty.EASY,
            "Given a string s, determine whether it is a palindrome (reads the same forwards and backwards). Ignore case and non-alphanumeric characters.",
            "public class Solution {\n    public boolean isPalindrome(String s) {\n        // Write your solution here\n        return false;\n    }\n}"
        ));
        problems.add(createProblem(
            "Count Vowels in String",
            Difficulty.EASY,
            "Given a string s, return the total count of vowels (a, e, i, o, u, both uppercase and lowercase) present in the string.",
            "public class Solution {\n    public int countVowels(String s) {\n        // Write your solution here\n        return 0;\n    }\n}"
        ));
        problems.add(createProblem(
            "Sum of Array Elements",
            Difficulty.EASY,
            "Given an array of integers nums, compute and return the sum of all elements.",
            "public class Solution {\n    public int arraySum(int[] nums) {\n        // Write your solution here\n        return 0;\n    }\n}"
        ));
        problems.add(createProblem(
            "Factorial Using Recursion",
            Difficulty.EASY,
            "Given a non-negative integer n, compute its factorial using recursion. Factorial of 0 is 1.",
            "public class Solution {\n    public long factorial(int n) {\n        // Write your recursive solution here\n        return 1L;\n    }\n}"
        ));
        problems.add(createProblem(
            "Even Numbers Counter",
            Difficulty.EASY,
            "Given an array of integers nums, return the count of numbers that are even (divisible by 2).",
            "public class Solution {\n    public int countEvens(int[] nums) {\n        // Write your solution here\n        return 0;\n    }\n}"
        ));

        // MEDIUM Problems (7)
        problems.add(createProblem(
            "Contains Duplicate",
            Difficulty.MEDIUM,
            "Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.",
            "public class Solution {\n    public boolean containsDuplicate(int[] nums) {\n        // Write your solution here\n        return false;\n    }\n}"
        ));
        problems.add(createProblem(
            "Move Zeroes",
            Difficulty.MEDIUM,
            "Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements. Must be done in-place.",
            "public class Solution {\n    public void moveZeroes(int[] nums) {\n        // Write your in-place solution here\n    }\n}"
        ));
        problems.add(createProblem(
            "First Non-Repeating Character",
            Difficulty.MEDIUM,
            "Given a string s, find the first non-repeating character in it and return its index. If it does not exist, return -1.",
            "public class Solution {\n    public int firstUniqChar(String s) {\n        // Write your solution here\n        return -1;\n    }\n}"
        ));
        problems.add(createProblem(
            "Fibonacci Sequence (Recursive)",
            Difficulty.MEDIUM,
            "The Fibonacci numbers form a sequence where each number is the sum of the two preceding ones, starting from 0 and 1. Given n, calculate F(n) using recursion.",
            "public class Solution {\n    public int fib(int n) {\n        // Write your recursive solution here\n        return 0;\n    }\n}"
        ));
        problems.add(createProblem(
            "Rotate Array by K Positions",
            Difficulty.MEDIUM,
            "Given an integer array nums, rotate the array to the right by k steps, where k is non-negative.",
            "public class Solution {\n    public void rotate(int[] nums, int k) {\n        // Write your solution here\n    }\n}"
        ));
        problems.add(createProblem(
            "Valid Anagram",
            Difficulty.MEDIUM,
            "Given two strings s and t, return true if t is an anagram of s, and false otherwise. An anagram is a word formed by rearranging letters.",
            "public class Solution {\n    public boolean isAnagram(String s, String t) {\n        // Write your solution here\n        return false;\n    }\n}"
        ));
        problems.add(createProblem(
            "Binary Search",
            Difficulty.MEDIUM,
            "Given an array of integers nums which is sorted in ascending order, and an integer target, write a function to search target in nums. If target exists, return its index. Otherwise, return -1. Time complexity must be O(log n).",
            "public class Solution {\n    public int search(int[] nums, int target) {\n        // Write your binary search here\n        return -1;\n    }\n}"
        ));

        // HARD Problems (3)
        problems.add(createProblem(
            "Longest Substring Without Repeating Characters",
            Difficulty.HARD,
            "Given a string s, find the length of the longest substring without duplicate characters using a sliding window or two-pointer technique.",
            "public class Solution {\n    public int lengthOfLongestSubstring(String s) {\n        // Write your sliding window solution here\n        return 0;\n    }\n}"
        ));
        problems.add(createProblem(
            "Merge Sorted Arrays",
            Difficulty.HARD,
            "You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n representing the number of elements in nums1 and nums2 respectively. Merge nums2 into nums1 in-place.",
            "public class Solution {\n    public void merge(int[] nums1, int m, int[] nums2, int n) {\n        // Write your in-place merge here\n    }\n}"
        ));
        problems.add(createProblem(
            "Tower of Hanoi",
            Difficulty.HARD,
            "The Tower of Hanoi is a classic recursion puzzle. Given n disks and three rods (source, auxiliary, destination), calculate the minimum number of moves required to transfer all disks from source to destination.",
            "public class Solution {\n    public int towerOfHanoiMoves(int n) {\n        // Write your solution here\n        return 0;\n    }\n}"
        ));

        List<DsaProblem> toSave = problems.stream()
                .filter(p -> !dsaProblemRepository.existsByTitle(p.getTitle()))
                .toList();

        if (!toSave.isEmpty()) {
            dsaProblemRepository.saveAll(toSave);
            log.info("Successfully seeded {} new DSA problems. Total now: {}", toSave.size(), dsaProblemRepository.count());
        } else {
            log.info("All {} DSA problems already present.", problems.size());
        }
    }

    private DsaProblem createProblem(String title, Difficulty difficulty, String description, String starterCode) {
        DsaProblem p = new DsaProblem();
        p.setTitle(title);
        p.setDifficulty(difficulty);
        p.setDescription(description);
        p.setStarterCode(starterCode);
        return p;
    }
}
