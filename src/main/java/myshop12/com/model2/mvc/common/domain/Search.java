package myshop12.com.model2.mvc.common.domain;


//==>리스트화면을 모델링(추상화/캡슐화)한 Bean
public class Search {

    ///Field
    private int currentPage;
    private String searchCondition;//상품번호,상품명,상품가격
    private String searchKeyword;//검색어
    private String searchType;//가격높은순
    private int searchBoundFirst;
    private int searchBoundEnd;
    private int pageSize;
    //==> 리스트화면 currentPage에 해당하는 회원정보를 ROWNUM 사용 SELECT 위해 추가된 Field
    //==> UserMapper.xml 의
    //==> <select  id="getUserList"  parameterType="search"	resultMap="userSelectMap">
    //==> 참조
    private int endRowNum;
    private int startRowNum;

    ///Constructor
    public Search() {
    }

    ///Method
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int paseSize) {
        this.pageSize = paseSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
    public int getSearchBoundFirst() {
        return searchBoundFirst;
    }

    public void setSearchBoundFirst(int searchBoundFirst) {
        this.searchBoundFirst = searchBoundFirst;
    }

    public int getSearchBoundEnd() {
        return searchBoundEnd;
    }

    public void setSearchBoundEnd(int searchBoundEnd) {
        this.searchBoundEnd = searchBoundEnd;
    }

    //==> Select Query 시 ROWNUM 마지막 값 3*5 = 15번
    public int getEndRowNum() {
        return getCurrentPage() * getPageSize();
    }

    //==> Select Query 시 ROWNUM 시작 값 2 *5 + 1 = 11번 커렌페 1 pageSize 최대값하면
    public int getStartRowNum() {
        return (getCurrentPage() - 1) * getPageSize() + 1;
    }

    @Override
    public String toString() {
        return "Search [currentPage=" + currentPage + ", searchCondition=" + searchCondition + ", searchKeyword="
				+ searchKeyword + ", searchType=" + searchType + ", pageSize=" + pageSize + ", endRowNum=" + endRowNum
				+ ", startRowNum=" + startRowNum + "]";
    }

}