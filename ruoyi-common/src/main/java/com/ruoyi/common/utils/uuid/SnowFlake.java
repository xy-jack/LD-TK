package com.ruoyi.common.utils.uuid;

import lombok.extern.slf4j.Slf4j;


/**
 * 唯一id生成器
 */
@Slf4j
public class SnowFlake {
    
    // 起始的时间戳2019-01-01
    private final static long START_STMP = 1546272000000L;
    
    // 每一部分占用的位数
    private final static long SEQUENCE_BIT = 10;// 序列号占用的位数
    
    private final static long MACHINE_BIT = 9; // 机器标识占用的位数
    
    private final static long DATACENTER_BIT = 3;// 数据中心占用的位数
    // 每一部分最大值
    
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    
    // 每一部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
    
     private long datacenterId; // 数据中心
    
    private long machineId; // 机器标识
    
    private long sequence = 0L; // 序列号
    
    private long lastStmp = -1L;// 上一次时间戳
    
    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException(String.format("machineId [%d] can't be greater than MAX_MACHINE_NUM or less than 0", machineId));
        }
        this.machineId = machineId;
    }
    
    // 产生下一个ID
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            try {
                throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
            } catch (Throwable e) {
                log.error("nextId", e);
            }
        }
        
        if (currStmp == lastStmp) {
            // if条件里表示当前调用和上一次调用落在了相同毫秒内，只能通过第三部分，序列号自增来判断为唯一，所以+1.
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大，只能等待下一个毫秒
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            // 执行到这个分支的前提是currTimestamp > lastTimestamp，说明本次调用跟上次调用对比，已经不再同一个毫秒内了，这个时候序号可以重新回置0了。
            sequence = 0L;
        }
        
        lastStmp = currStmp;
        // 就是用相对毫秒数、机器ID和自增序号拼接
        return (currStmp - START_STMP) << TIMESTMP_LEFT // 时间戳部分 //| datacenterId << DATACENTER_LEFT // 数据中心部分
            | machineId << MACHINE_LEFT // 机器标识部分
            | sequence; // 序列号部分
    }
    
    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }
    
    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public static long getMaxMachineNum(){
        return MAX_MACHINE_NUM;
    }
}
