package com.maquinadebusca.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LinkPage implements Serializable{
	private static final long serialVersionUID = 7363704570050260799L;
	
	List<Link> page = new ArrayList<>();

	public LinkPage(List<Link> page) {
		this.page = page;
	}

	public List<Link> getPage() {
		return page;
	}

	public void setPage(List<Link> page) {
		this.page = page;
	}

}
