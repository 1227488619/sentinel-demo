package com.test.demo;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        initFlowRules();
        while (true) {
            get();
        }
    }

    @SentinelResource(value = "get", fallback = "exceptionHandler")
    public static void get(){
        System.out.println("hello world");
    }
    public static void exceptionHandler(){
        System.out.println("block!");
    }

    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("get");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
