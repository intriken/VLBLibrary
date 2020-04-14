package vlb.ide.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

public class SheetStore {

	private String x = null;
	private String DropNum = null;
	private String Proj = null;
	private String CRNum = null;
	private String CRName = null;
	private String CRDesc = null;
	private String CRLApp = null;
	private String Changetype = null;
	private String DBApp = null;
	private String TableName = null;
	private String FieldName = null;
	private String DataType = null;
	private String ChangeDesc = null;
	private String PK = null;
	private String Manditory = null;
	private String FK = null;
	private String ValidValues = null;
	private String Responsiblity = null;
	private String DBStatus = null;
	private String WSDefect = null;
	private String WSTT = null;
	private String WSImp = null;
	private String RetDefect = null;
	private String RetTT = null;
	private String RetImp = null;
	private String Comments = null;
	private String CreateDate = null;
	private String UpdateDate = null;
	private String UpdatedBy = null;

	public SheetStore(){

	}

	public SheetStore( String x, String DropNum, String Proj, String CRNum, String CRName,	 String CRDesc, 
			String CRLApp,  String Changetype, String DBApp, String TableName, String FieldName, String DataType,
			String ChangeDesc, String PK, String Manditory, String FK, String ValidValues, String Responsiblity, String DBStatus, 
			String WSDefect, String WSTT, String WSImp, String RetDefect, String RetTT, String RetImp, String Comments,
			String CreateDate,  String UpdateDate, String UpdatedBy){
		this.x = x;
		this.DropNum = DropNum;
		this.Proj = Proj;
		this.CRNum = CRNum;
		this.CRName = CRName;
		this.CRDesc = CRDesc;
		this.CRLApp = CRLApp;
		this.Changetype = Changetype;
		this.DBApp = DBApp;
		this.TableName = TableName;
		this.FieldName = FieldName;
		this.DataType = DataType;
		this.ChangeDesc = ChangeDesc;
		this.PK = PK;
		this.Manditory = Manditory;
		this.FK = FK;
		this.ValidValues = ValidValues;
		this.Responsiblity = Responsiblity;
		this.DBStatus = DBStatus;
		this.WSDefect = WSDefect;
		this.WSTT = WSTT;
		this.WSImp = WSImp;
		this.RetDefect = RetDefect;
		this.RetTT = RetTT;
		this.RetImp = RetImp;
		this.Comments = Comments;
		this.CreateDate = CreateDate;
		this.UpdateDate = UpdateDate;
		this.UpdatedBy = UpdatedBy;
	}

	public void writeRow(Row row , CreationHelper createHelper){
		Cell cell = row.createCell(0);
		cell.setCellValue(createHelper.createRichTextString(x));
		cell = row.createCell(1);		
		cell.setCellValue(createHelper.createRichTextString(DropNum));
		cell = row.createCell(2);
		cell.setCellValue(createHelper.createRichTextString(Proj));
		cell = row.createCell(3);
		cell.setCellValue(createHelper.createRichTextString(CRNum));
		cell = row.createCell(4);
		cell.setCellValue(createHelper.createRichTextString(CRName));
		cell = row.createCell(5);
		cell.setCellValue(createHelper.createRichTextString(CRDesc));
		cell = row.createCell(6);
		cell.setCellValue(createHelper.createRichTextString(CRLApp));
		cell = row.createCell(7);
		cell.setCellValue(createHelper.createRichTextString(Changetype));
		cell = row.createCell(8);
		cell.setCellValue(createHelper.createRichTextString(DBApp));
		cell = row.createCell(9);
		cell.setCellValue(createHelper.createRichTextString(TableName));
		cell = row.createCell(10);
		cell.setCellValue(createHelper.createRichTextString(FieldName));
		cell = row.createCell(11);
		cell.setCellValue(createHelper.createRichTextString(DataType));
		cell = row.createCell(12);
		cell.setCellValue(createHelper.createRichTextString(ChangeDesc));
		cell = row.createCell(13);
		cell.setCellValue(createHelper.createRichTextString(PK));
		cell = row.createCell(14);
		cell.setCellValue(createHelper.createRichTextString(Manditory));
		cell = row.createCell(15);
		cell.setCellValue(createHelper.createRichTextString(FK));
		cell = row.createCell(16);
		cell.setCellValue(createHelper.createRichTextString(ValidValues));
		cell = row.createCell(17);
		cell.setCellValue(createHelper.createRichTextString(Responsiblity));
		cell = row.createCell(18);
		cell.setCellValue(createHelper.createRichTextString(DBStatus));
		cell = row.createCell(19);
		cell.setCellValue(createHelper.createRichTextString(WSDefect));
		cell = row.createCell(20);
		cell.setCellValue(createHelper.createRichTextString(WSTT));
		cell = row.createCell(21);
		cell.setCellValue(createHelper.createRichTextString(WSImp));
		cell = row.createCell(22);
		cell.setCellValue(createHelper.createRichTextString(RetDefect));
		cell = row.createCell(23);
		cell.setCellValue(createHelper.createRichTextString(RetTT));
		cell = row.createCell(24);
		cell.setCellValue(createHelper.createRichTextString(RetImp));
		cell = row.createCell(25);
		cell.setCellValue(createHelper.createRichTextString(Comments));
		cell = row.createCell(26);
		cell.setCellValue(createHelper.createRichTextString(CreateDate));
		cell = row.createCell(27);
		cell.setCellValue(createHelper.createRichTextString(UpdateDate));
		cell = row.createCell(28);
		cell.setCellValue(createHelper.createRichTextString(UpdatedBy));
		cell = row.createCell(29);
	}
	
	public String getChangetype() {
		return Changetype;
	}

	public void setChangetype(String changetype) {
		Changetype = changetype;
	}

	public String getComments() {
		return Comments;
	}

	public void setComments(String comments) {
		Comments = comments;
	}

	public String getCRDesc() {
		return CRDesc;
	}

	public void setCRDesc(String desc) {
		CRDesc = desc;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public String getCRLApp() {
		return CRLApp;
	}

	public void setCRLApp(String app) {
		CRLApp = app;
	}

	public String getCRName() {
		return CRName;
	}

	public void setCRName(String name) {
		CRName = name;
	}

	public String getCRNum() {
		return CRNum;
	}

	public void setCRNum(String num) {
		CRNum = num;
	}

	public String getDBApp() {
		return DBApp;
	}

	public void setDBApp(String app) {
		DBApp = app;
	}

	public String getDBStatus() {
		return DBStatus;
	}

	public void setDBStatus(String status) {
		DBStatus = status;
	}

	public String getDropNum() {
		return DropNum;
	}

	public void setDropNum(String dropNum) {
		DropNum = dropNum;
	}

	public String getProj() {
		return Proj;
	}

	public void setProj(String proj) {
		Proj = proj;
	}

	public String getResponsiblity() {
		return Responsiblity;
	}

	public void setResponsiblity(String responsiblity) {
		Responsiblity = responsiblity;
	}

	public String getRetDefect() {
		return RetDefect;
	}

	public void setRetDefect(String retDefect) {
		RetDefect = retDefect;
	}

	public String getRetImp() {
		return RetImp;
	}

	public void setRetImp(String retImp) {
		RetImp = retImp;
	}

	public String getRetTT() {
		return RetTT;
	}

	public void setRetTT(String retTT) {
		RetTT = retTT;
	}

	public String getTableName() {
		return TableName;
	}

	public void setTableName(String tableName) {
		TableName = tableName;
	}

	public String getUpdateDate() {
		return UpdateDate;
	}

	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}

	public String getWSDefect() {
		return WSDefect;
	}

	public void setWSDefect(String defect) {
		WSDefect = defect;
	}

	public String getWSImp() {
		return WSImp;
	}

	public void setWSImp(String imp) {
		WSImp = imp;
	}

	public String getWSTT() {
		return WSTT;
	}

	public void setWSTT(String wstt) {
		WSTT = wstt;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getChangeDesc() {
		return ChangeDesc;
	}

	public void setChangeDesc(String changeDesc) {
		ChangeDesc = changeDesc;
	}

	public String getDataType() {
		return DataType;
	}

	public void setDataType(String dataType) {
		DataType = dataType;
	}

	public String getFieldName() {
		return FieldName;
	}

	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}

	public String getFK() {
		return FK;
	}

	public void setFK(String fk) {
		FK = fk;
	}

	public String getManditory() {
		return Manditory;
	}

	public void setManditory(String manditory) {
		Manditory = manditory;
	}

	public String getPK() {
		return PK;
	}

	public void setPK(String pk) {
		PK = pk;
	}

	public String getValidValues() {
		return ValidValues;
	}

	public void setValidValues(String validValues) {
		ValidValues = validValues;
	}

}
