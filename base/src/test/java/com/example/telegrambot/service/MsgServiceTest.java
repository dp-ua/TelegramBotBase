package com.example.telegrambot.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MsgServiceTest {
    MsgService msgService;

    @Before
    public void setup() {
        msgService = new MsgService();
    }

    @Test
    public void shouldContainsAStringWithDelimeters_case1() {
        Assert.assertEquals(true, msgService.contains("instr", "in big instr string"));
    }

    @Test
    public void shouldContainsAStringWithDelimeters_case2() {
        Assert.assertEquals(true, msgService.contains("instr", "instr in big  string"));
    }

    @Test
    public void shouldContainsAStringWithDelimeters_case3() {
        Assert.assertEquals(true, msgService.contains("instr", "in big string instr"));
    }

    @Test
    public void shouldContainsAStringWithDelimeters_case4() {
        Assert.assertEquals(true, msgService.contains("instr", "in big instr, string"));
    }

    @Test
    public void shouldContainsAStringWithDelimeters_case5() {
        Assert.assertEquals(true, msgService.contains("long instr text", "in big long instr text. string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case1() {
        Assert.assertEquals(false, msgService.contains("instr", "in biginstr, string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case2() {
        Assert.assertEquals(false, msgService.contains("instr", "in big inst r string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case3() {
        Assert.assertEquals(false, msgService.contains(" ", "in big inst r string"));
    }
    @Test
    public void shouldNotContainsAStringWithDelimeters_case3a() {
        Assert.assertEquals(false, msgService.contains(".", "in big inst r string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case4() {
        Assert.assertEquals(false, msgService.contains(null, "in big inst r string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case5() {
        Assert.assertEquals(false, msgService.contains(null, null));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case6() {
        Assert.assertEquals(false, msgService.contains("str", null));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case7() {
        Assert.assertEquals(false, msgService.contains("", "in big inst r string"));
    }
}