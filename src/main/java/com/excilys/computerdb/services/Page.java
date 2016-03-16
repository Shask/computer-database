package com.excilys.computerdb.services;

/**
 * Class used in DAO and services allowing you to control parameters such as the page Size, the
 * order, ...
 * 
 * @author Steven Fougeron
 *
 */
public class Page {
  private int currentPage = 1;
  private int pageSize = 50;
  private Direction asc = Direction.ASC;
  private OrderBy order = OrderBy.ID;

  public enum Direction {
    DESC, ASC
  }

  public enum OrderBy {
    ID, NAME, INTRODUCED, DISCONTINUED, COMPANY_NAME;
  }

  public OrderBy getOrder() {
    return order;
  }
  
  /**
   * Change order, if order is the same, change the direction.
   * 
   * @param order to set
   */
  public void setOrder(OrderBy order) {
    if ( this.order != order ) {
      this.order = order;
      this.asc = Direction.ASC;
    } else {
      switchDirection();
    }
  }
  
  public Direction getAsc() {
    return asc;
  }
  
  /**
   * swich from ASC to Desc and Desc to ASC.
   */
  public void switchDirection() {
    if ( asc == Direction.ASC ) {
      asc = Direction.DESC;
    } else {
      asc = Direction.ASC;
    }
  }

  /**
   * Constructor of page using currentPage and pageSize.
   * @param currentPage to set
   * @param pageSize to set
   */
  public Page(int currentPage, int pageSize) {
    super();
    this.currentPage = currentPage;
    this.pageSize = pageSize;
  }

  public Page() {
    super();
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public void resetPage() {
    currentPage = 1;
  }

  public void incPage() {
    currentPage++;
  }

  /**
   * Decrement currentPage if currentPage>1.
   */
  public void decPage() {
    if ( currentPage > 1 ) {
      currentPage--;
    }
  }

}
