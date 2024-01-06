package com.oracle.projectGo.service;

import lombok.Data;

@Data
public class BoardPaging {
    private int currentPage = 1;	private int rowPage = 10;
    private int pageBlock   = 10;
    private int start;				private int end;
    private int startPage;			private int endPage;
    private int total;				private int totalPage;
    int pageSize;

    public BoardPaging(int total, String currentPage1, int pageSize) {
        this.total = total;  //140
        this.rowPage = pageSize;

        if (currentPage1 != null) {
            this.currentPage = Integer.parseInt(currentPage1); // 2

        }
        //				1				10
        start 	= (currentPage - 1) * rowPage + 1;	//시작시  1	11
        end		= start + rowPage - 1;				//시작시 10	20

        //									25    /   10
        totalPage = (int) Math.ceil((double)total / rowPage);
        //				2			2
        startPage = currentPage - (currentPage - 1) % pageBlock; // 시작시1
        endPage = startPage + pageBlock - 1; //10
        //		10		14
        if (endPage > totalPage) {
            endPage = totalPage;
        }

    }

}