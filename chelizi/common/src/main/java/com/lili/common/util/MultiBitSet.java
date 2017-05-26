/**
 * Date: 2013-6-20
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */

package com.lili.common.util;

import java.util.Arrays;

/**
 * java标准库只实现了bitset，每次操作1位，实际项目中需要操作小于8位但大于1位的数据集的时候就无能为力了。
 * 
 * @author cookie.hu
 */
public class MultiBitSet implements Cloneable
{
    private final static int ADDRESS_BITS_PER_WORD = 6;
    private final static int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;

    /** 默认的最小单元位数 */
    private final static int DEFAULT_UNITSIZE = 1;

    /** 默认的最小单元的个数 */
    private final static int DEFAULT_UNITCOUNT = 8;

    /** 默认的每次扩展添加的word数目 */
    private final static int DEFAULT_EXPAND = 1024;

    /** 实际存储数据 */
    private long[] words;

    /**
     * 多少个bit作为一个unit(官方的bitset相当于此处的unitSize=1,byte数组相当于此处的unitSize=8)
     * unitSize这里只支持1 <= unitSize <= 8 且必须为2的幂数(1,2,4,8(后期可扩展成支持任意位数))
     * */
    private final int unitSize;

    /** 当前实际上申请了多少个word内存单元 */
    private int wordsInUse = 0;

    /**
     * 创建一个MultiBitSet
     * 
     * @param unitSize
     *            最小单元所占的位数
     * @param unitCount
     *            初始化多少个最小单元
     */
    public MultiBitSet(int unitSize, int unitCount)
    {
        super();
        if (unitSize < 0)
            throw new NegativeArraySizeException("unitSize < 0: " + unitSize);

        if (unitCount < 0)
            throw new NegativeArraySizeException("unitCount < 0: " + unitCount);

        // 如果参数错误，则给个默认值
        if (unitSize > 8)
        {
            unitSize = DEFAULT_UNITSIZE;
            unitCount = DEFAULT_UNITCOUNT;
        }
        this.unitSize = unitSize;
        initWords(unitCount);
    }

    public MultiBitSet(int unitSize)
    {
        super();
        if (unitSize < 0)
            throw new NegativeArraySizeException("unitSize < 0: " + unitSize);

        if (unitSize > 8)
            throw new IllegalArgumentException(
                    "unitSize should not bigger than 8.");

        if (unitSize != 1 && unitSize % 2 != 0)
            throw new IllegalArgumentException(
                    "unitSize value only can be 1,2,4,8");

        // 如果参数错误，则给个默认值
        if (unitSize > 8)
        {
            unitSize = DEFAULT_UNITSIZE;
        }
        this.unitSize = unitSize;
        initWords(DEFAULT_UNITCOUNT);
    }

    /**
     * 返回当前的unit的word索引
     * 
     * @param unitIndex
     *            unit索引，从0开始
     * @return word索引，从0开始
     */
    private int wordIndex(int unitIndex)
    {
        return ((unitIndex + 1) * unitSize - 1) >> ADDRESS_BITS_PER_WORD;
    }

    /**
     * 初始化内存
     * 
     * @param nUnits
     *            unit的个数
     */
    private void initWords(int nUnits)
    {
        words = new long[wordIndex(nUnits - 1) + 1];
    }

    /**
     * 设置unitIndex指向的块值
     * 
     * @param unitIndex
     *            要设置的块的下标
     * @param value
     *            要设置的值(如果unitSize < 8,比如unitSize = 4,则value只后4位生效)
     */
    public void set(int unitIndex, byte value)
    {
        if (unitIndex < 0)
            throw new NegativeArraySizeException("unitIndex < 0: " + unitIndex);

        if (value >= (1 << unitSize))
            throw new IllegalArgumentException(unitIndex
                    + " pos value should not bigger than "
                    + ((1 << unitSize) - 1));

        int wordIndex = wordIndex(unitIndex);
        expandTo(wordIndex);

        // 计算mask
        long valueMask = (1 << unitSize) - 1;
        long valueMask1 = valueMask;
        valueMask &= value;
        int unitSizePerWord = BITS_PER_WORD / unitSize;
        unitIndex %= unitSizePerWord;
        unitSizePerWord -= (unitIndex + 1);
        valueMask <<= (unitSizePerWord * unitSize);
        valueMask1 <<= (unitSizePerWord * unitSize);
        valueMask1 = ~valueMask1;
        words[wordIndex] &= valueMask1;
        words[wordIndex] |= valueMask;
    }

    /**
     * 得到一个unit的值
     * 
     * @param unitIndex
     * @return
     */
    public byte get(int unitIndex)
    {
        if (unitIndex < 0)
            throw new IndexOutOfBoundsException("unitIndex < 0: " + unitIndex);

        long valueMask = (1 << unitSize) - 1;
        long valueMask1 = valueMask;
        int wordIndex = wordIndex(unitIndex);

        if (wordIndex >= wordsInUse)
        {
            throw new IndexOutOfBoundsException("bitIndex < 0: " + unitIndex);
        }
        int unitSizePerWord = BITS_PER_WORD / unitSize;
        unitIndex %= unitSizePerWord;
        unitSizePerWord -= (unitIndex + 1);
        valueMask <<= (unitSizePerWord * unitSize);
        valueMask = (words[wordIndex] & valueMask);
        valueMask >>= (unitSizePerWord * unitSize);
        valueMask &= valueMask1;
        return (byte) valueMask;
    }

    /**
     * 申请的内存不够时重新申请
     * 
     * @param wordIndex
     *            .
     */
    private void expandTo(int wordIndex)
    {
        int wordsRequired = wordIndex + 1;
        if (wordsInUse < wordsRequired)
        {
            ensureCapacity(wordsRequired);
            wordsInUse = wordsRequired;
        }
    }

    /**
     * 保证容量
     * 
     * @param wordsRequired
     */
    private void ensureCapacity(int wordsRequired)
    {
        if (words.length < wordsRequired)
        {
            // Allocate larger of (length + DEFAULT_EXPAND) size or required
            // size
            int request = Math
                    .max(words.length + DEFAULT_EXPAND, wordsRequired);
            words = Arrays.copyOf(words, request);
        }
    }

    /**
     * 实现克隆接口
     */
    public MultiBitSet clone()
    {
        MultiBitSet o = null;
        try
        {
            o = (MultiBitSet) super.clone();
            o.words = (long[]) words.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return o;
    }

    public static void main(String[] args)
    {
        MultiBitSet multiBitSet = new MultiBitSet(2);
        for (int i = 0; i < 100; i++)
        {
            multiBitSet.set(i, (byte) (i % 4));
        }
        MultiBitSet multiBitSet2 = multiBitSet.clone();
        for (int i = 100; i > 0; i--)
        {
            multiBitSet.set(i, (byte) (i % 4));
        }
        
        System.out.println(multiBitSet.get(45));
        System.out.println(multiBitSet2.get(67));
    }
}
