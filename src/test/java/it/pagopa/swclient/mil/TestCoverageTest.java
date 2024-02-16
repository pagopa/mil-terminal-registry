package it.pagopa.swclient.mil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestCoverageTest {

  @Test
  void testCoverageShouldReturnTrue() {
    assertTrue(new TestCoverage().hello());
  }
}