package com.file.service.model;

import org.junit.Assert;
import org.junit.Test;

public class EmployeeDataTest {

    @Test
    public void testProviderAssociationDataWithFullArguments() {

        EmployeeData employeeData = new EmployeeData();
        employeeData.setName("Test");
        employeeData.setEmployeeId("234");

        employeeData.toString();

        Assert.assertEquals("234", employeeData.getEmployeeId());
        Assert.assertEquals("Test", employeeData.getName());


    }
}
