package com.adgaudio.mysterytrackingnumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TrackingNumberTest {
    List<String> validTrackingNumbers = Arrays.asList(
            "73891051146", "3318810025");
    List<String> courierNames = Arrays.asList(
            "DHL Express Air", "DHL Express");

    @Test
    public void testParseInvalid() {
        assertNull(TrackingNumber.parse("invalid tracking number"));
        assertNull(TrackingNumber.parse(""));
    }
    
    @Test
    public void testParseValid() {
        for (int i=0 ; i<validTrackingNumbers.size(); i++) {
            TrackingNumber tn = TrackingNumber.parse(validTrackingNumbers.get(i));
            assertNotNull("Should be a recognized tracking number: " + validTrackingNumbers.get(i), tn);
            assertEquals(tn.courier.getName(), courierNames.get(i));
        }
    }
    
    @Test
    public void testFilterAndParseTrackingNumbers() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("not trackingnumber");
        arr.addAll(validTrackingNumbers);
        arr.add(validTrackingNumbers.get(0) + " ");
        arr.add(" " + validTrackingNumbers.get(0));
        
        List<TrackingNumber> res = TrackingNumber.filterAndParseTrackingNumbers(arr);
        assertEquals(res.size(), validTrackingNumbers.size());
        for (TrackingNumber tn : res) {
            assertNotNull("filterAndParseTrackingNumbers should never return a null value", tn);
        }
    }
    
    @Test
    public void testEquals() {
        TrackingNumber tn0 = TrackingNumber.parse(validTrackingNumbers.get(0));
        TrackingNumber tn1 = TrackingNumber.parse(validTrackingNumbers.get(1));
        assertEquals(tn0, tn0);
        assertNotEquals(tn0, tn1);
    }

    @Test
    public void testHashCode() {
        TrackingNumber tn0 = TrackingNumber.parse(validTrackingNumbers.get(0));
        assertEquals(tn0.hashCode(), validTrackingNumbers.get(0).hashCode());
    }
}