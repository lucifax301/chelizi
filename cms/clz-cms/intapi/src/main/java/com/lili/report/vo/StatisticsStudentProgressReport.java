package com.lili.report.vo;

import java.util.List;

import com.lili.cms.entity.BasePagedEntity;

/**
 * 学员进度统计报表
 * @author devil
 *
 */
public class StatisticsStudentProgressReport extends BasePagedEntity{

	private int cityId;
	
	private String city;
	
	private String school;
	
	private int step4;
	
	private int step5;

	private int step6;
	
	private int step101;
	
	private int step201;
	
	private int step301;
	
	private int step302;
	
	private int step401;
	
	private int step402;
	
	private int step501;
	
	private int step601;
	
	private int step602;
	
	private int step701;
	
	private int step702;
	
	private int step801;
	
	private List<StatisticsStudentProgressReport> reports;
	
	
	
	public List<StatisticsStudentProgressReport> getReports() {
		return reports;
	}

	public void setReports(List<StatisticsStudentProgressReport> reports) {
		this.reports = reports;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public void addStep4(int value){
		step4=step4+value;
	}

	public void addStep5(int value){
		step5=step5+value;
	}
	
	public void addStep6(int value){
		step6=step6+value;
	}
	
	public void addStep101(int value){
		step101=step101+value;
	}
	
	public void addStep201(int value){
		step201=step201+value;
	}
	
	public void addStep301(int value){
		step301=step301+value;
	}
	
	public void addStep302(int value){
		step302=step302+value;
	}
	
	public void addStep401(int value){
		step401=step401+value;
	}
	
	public void addStep402(int value){
		step402=step402+value;
	}
	
	public void addStep501(int value){
		step501=step501+value;
	}
	
	public void addStep601(int value){
		step601=step601+value;
	}
	
	public void addStep602(int value){
		step602=step602+value;
	}
	
	public void addStep701(int value){
		step701=step701+value;
	}
	
	public void addStep702(int value){
		step702=step702+value;
	}
	
	public void addStep801(int value){
		step801=step801+value;
	}
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getStep4() {
		return step4;
	}

	public void setStep4(int step4) {
		this.step4 = step4;
	}

	public int getStep5() {
		return step5;
	}

	public void setStep5(int step5) {
		this.step5 = step5;
	}

	public int getStep6() {
		return step6;
	}

	public void setStep6(int step6) {
		this.step6 = step6;
	}

	public int getStep101() {
		return step101;
	}

	public void setStep101(int step101) {
		this.step101 = step101;
	}

	public int getStep201() {
		return step201;
	}

	public void setStep201(int step201) {
		this.step201 = step201;
	}

	public int getStep301() {
		return step301;
	}

	public void setStep301(int step301) {
		this.step301 = step301;
	}

	public int getStep302() {
		return step302;
	}

	public void setStep302(int step302) {
		this.step302 = step302;
	}

	public int getStep401() {
		return step401;
	}

	public void setStep401(int step401) {
		this.step401 = step401;
	}

	public int getStep402() {
		return step402;
	}

	public void setStep402(int step402) {
		this.step402 = step402;
	}

	public int getStep501() {
		return step501;
	}

	public void setStep501(int step501) {
		this.step501 = step501;
	}

	public int getStep601() {
		return step601;
	}

	public void setStep601(int step601) {
		this.step601 = step601;
	}

	public int getStep602() {
		return step602;
	}

	public void setStep602(int step602) {
		this.step602 = step602;
	}

	public int getStep701() {
		return step701;
	}

	public void setStep701(int step701) {
		this.step701 = step701;
	}

	public int getStep702() {
		return step702;
	}

	public void setStep702(int step702) {
		this.step702 = step702;
	}

	public int getStep801() {
		return step801;
	}

	public void setStep801(int step801) {
		this.step801 = step801;
	}
	
	
}

