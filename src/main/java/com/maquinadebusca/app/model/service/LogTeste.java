package com.maquinadebusca.app.model.service;

public class LogTeste {

	public static void main(String[] args) {
//		for(int i =0; i<10000; i++) {
//			System.out.println(i+"). "+log(i,2));
//		}
		
		System.out.println(4/3);
		System.out.println(4/3L);
		System.out.println(((double)4)/((double)3));
	}
	static int log(int x, int base)
	{
		return (int) (Math.log(x) / Math.log(base));
	}

}
