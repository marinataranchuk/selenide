package com.codeborne.selenide.collections;

import com.codeborne.selenide.UnitTests;
import com.codeborne.selenide.ex.ListSizeMismatch;
import com.codeborne.selenide.impl.WebElementsCollection;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListSizeTest extends UnitTests {
  @Test
  void testApplyWithEmptyList() {
    assertThat(new ListSize(10).apply(emptyList()))
      .isFalse();
  }

  @Test
  void testApplyWithWrongSizeList() {
    assertThat(new ListSize(10).apply(singletonList(mock(WebElement.class))))
      .isFalse();
  }

  @Test
  void testApplyWithCorrectListSize() {
    assertThat(new ListSize(1).apply(singletonList(mock(WebElement.class))))
      .isTrue();
  }

  @Test
  void testFailMethod() {
    WebElementsCollection mockedWebElementCollection = mock(WebElementsCollection.class);
    when(mockedWebElementCollection.description()).thenReturn("Collection description");

    try {
      new ListSize(10).fail(mockedWebElementCollection,
        emptyList(),
        new Exception("Exception message"),
        10000);
    } catch (ListSizeMismatch ex) {
      assertThat(ex.getMessage())
        .isEqualToIgnoringNewLines(": expected: = 10, actual: 0, collection: Collection descriptionElements: []");
    }
  }

  @Test
  void testToString() {
    assertThat(new ListSize(10).toString())
      .isEqualTo("size(10)");
  }
}
