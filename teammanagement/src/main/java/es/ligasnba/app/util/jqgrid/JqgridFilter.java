package es.ligasnba.app.util.jqgrid;

import java.util.ArrayList;

public class JqgridFilter {
	private String source;
	private String groupOp;
	private ArrayList<Rule> rules;
	public JqgridFilter() {
	super();
	}
	public JqgridFilter(String source) {
	super();
	this.source = source;
	}

	public String getGroupOp() {
		return groupOp;
	}
	public ArrayList<Rule> getRules() {
		return rules;
	}
	public String getSource() {
		return source;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	
	/**
	* Inner class containing field rules
	*/
	public static class Rule {
	private String junction;
	private String field;
	private String op;
	private String data;
	 
	public Rule() {}
	public Rule(String junction, String field, String op, String data) {
	super();
	this.junction = junction;
	this.field = field;
	this.op = op;
	this.data = data;
	}

	public String getData() {
		return data;
	}
	public String getField() {
		return field;
	}
	public String getJunction() {
		return junction;
	}
	public String getOp() {
		return op;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setField(String field) {
		this.field = field;
	}
	public void setJunction(String junction) {
		this.junction = junction;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
	}
	 
	}