package eluosiTest;

//再加一个判断，人能否到特定方向的位置
import java.util.ArrayDeque;
import java.util.Scanner;

import org.junit.Test;

class postion{
	int x;
	int y;
	postion(int x,int y){
		this.x=x;
		this.y=y;
	}
}

public class BoxGame {
	private int m;
	private int n;
	private int t;
	private int startx;
	private int starty;
	private int px;
	private int py;
	private int desx;
	private int desy;
	int[] totalresult;
	int[][] array;
	int[][] direction;
	public static int min=255;
	public static int result=0;
	BoxGame(){
		System.out.println("请输入测试的组数");
		Scanner scanner = new Scanner(System.in);
		t=scanner.nextInt();
		totalresult = new int[t];
		for(int i = 0;i<t;i++){
			min=255;
			result=0;
			System.out.println("输入第"+(i+1)+"组数据的行数和列数");
			m=scanner.nextInt();
			n=scanner.nextInt();
			array = new int[m][n];
			direction=new int[m][n];
			System.out.println("输入第"+(i+1)+"组的迷宫");
			for(int j=0;j<m;j++){
				for(int k=0;k<n;k++){
					array[j][k]=scanner.nextInt();
					if(array[j][k]<0||array[j][k]>5){
						System.out.println("Input data Error!");
						System.exit(1);
					}
				}

			}
			//PrintArray();
			Personlocation();
			findlocation();
			getDestination();
			GuessGame();
			if(min!=0&&min!=255){
				totalresult[i]=min;
				//System.out.printf("Min Steps %d!\n",min);
			}else{
				//System.out.printf("Not Found!\n",-1);
				totalresult[i]=-1;
			}
		}
		PrintResult();
	}
	
	public void PrintResult(){
		for(int i=0;i<totalresult.length;i++)
		{
			if(totalresult[i]!=-1){
				System.out.printf("Min Steps %d!\n",totalresult[i]);
			}else{
				System.out.printf("Not Found! %d!\n",-1);
			}
		}
	}
	public boolean up(int m,int n){
		if(m-1>=0&&array[m-1][n]!=1&&direction[m-1][n]!=1&&reachLocation(m+1,n))
			return true;
		else {
			return false;
		}
	}
	
	public boolean down(int m,int n){
		if(m+1<this.m&&array[m+1][n]!=1&&direction[m+1][n]!=1&&reachLocation(m-1,n))
			return true;
		else {
			return false;
		}
	}
	
	
	public boolean left(int m,int n){
		if(n-1>=0&&array[m][n-1]!=1&&direction[m][n-1]!=1&&reachLocation(m,n+1))
			return true;
		else {
			return false;
		}
	}
	
	public boolean right(int m,int n){
		if(n+1<this.n&&array[m][n+1]!=1&&direction[m][n+1]!=1&&reachLocation(m,n-1))
			return true;
		else {
			return false;
		}
	}
	
	//找到箱子位置
	public void findlocation(){
		boolean flag=false;
		for(int j=0;j<m;j++){
			for(int k=0;k<n;k++){
				if(array[j][k]==2){
					if(flag==true){
						System.out.println("Duplicate Box,Input Error!");
						System.exit(1);
					}else{
						startx=j;
						starty=k;
						direction[startx][starty]=1;
						flag=true;
					}
				}
			}
		}
	}
	
	public void Personlocation(){
		boolean flag=false;
		for(int j=0;j<m;j++){
			for(int k=0;k<n;k++){
				if(array[j][k]==4){
					if(flag==true){
						System.out.println("Duplicate Box,Input Error!");
						System.exit(1);
					}else{
						px=j;
						py=k;
						array[px][py]=0;
						flag=true;
					}
				}
			}
		}
	}
	
	public void getDestination(){
		boolean flag=false;
		for(int j=0;j<m;j++){
			for(int k=0;k<n;k++){
				if(array[j][k]==3){
					if(flag==true){
						System.out.println("Duplicate Box,Input Error!");
						System.exit(1);
					}else{
						desx=j;
						desy=k;
						flag=true;
					}
				}
			}
		}
	}
	
	//采用图的深度遍历确定人能否到达箱子搬运到特定位置时人的站位
	public boolean reachLocation(int x,int y){
		int[][] ptag=new int[array.length][];
		for(int i=0;i<array.length;i++){
			ptag[i]=array[i].clone();
		}
		if(px==x&&py==y){
			return true;
		}else if(x<0||x>=m||y>=n||y<0){
			return false;
		}
		ArrayDeque<postion> queue = new ArrayDeque<postion>();
		queue.add(new postion(px,py));
		ptag[px][py]=1;
		while(!queue.isEmpty()){
			postion p = queue.remove();
			if (p.x-1==x&&p.y==y&&array[p.x-1][p.y]==0) {
				return true;
			}else if(p.x-1>=0&&array[p.x-1][p.y]==0&&ptag[p.x-1][p.y]!=1){
				queue.add(new postion(p.x-1, p.y));
				ptag[p.x-1][p.y]=1;
			}
			if (p.x+1==x&&p.y==y&&array[p.x+1][p.y]==0) {
				return true;
			}else if(p.x+1<m&&array[p.x+1][p.y]==0&&ptag[p.x+1][p.y]!=1){
				queue.add(new postion(p.x+1, p.y));
				ptag[p.x+1][p.y]=1;
			}
			if (p.x==x&&p.y-1==y&&array[p.x][p.y-1]==0) {
				return true;
			}else if(p.y-1>=0&&array[p.x][p.y-1]==0&&ptag[p.x][p.y-1]!=1){
				queue.add(new postion(p.x, p.y-1));
				ptag[p.x][p.y-1]=1;
			}
			if (p.x==x&&p.y+1==y&&array[p.x][p.y+1]==0) {
				return true;
			}else if(p.y+1<n&&array[p.x][p.y+1]==0&&ptag[p.x][p.y+1]!=1){
				queue.add(new postion(p.x, p.y+1));
				ptag[p.x][p.y+1]=1;
			}
		}
		return false;
	}
	
	//使用回溯法判断是否能到到目的地，搬运的过程中需关注人的位置及箱子移动位置的配合问题
	public void GuessGame(){
			boolean flag=false;
			if(up(startx, starty)){
				flag=true;
				result++;
				array[startx][starty]=0;
				startx--;
				array[startx][starty]=2;
				direction[startx][starty]=1;
				if(Bingo()){
					if(result<min&&result!=0)
						{
							min=result;
						}
				}
				GuessGame();
				direction[startx][starty]=0;
				array[startx][starty]=0;
				startx++;
				array[startx][starty]=2;
				result--;

			}
			if(down(startx, starty)){
				flag=true;
				array[startx][starty]=0;
				startx++;
				array[startx][starty]=2;
				result++;
				direction[startx][starty]=1;
				if(Bingo()){
					if(result<min&&result!=0)
					{
						min=result;
					}
				}
				GuessGame();
				direction[startx][starty]=0;
				array[startx][starty]=0;
				startx--;
				result--;
				array[startx][starty]=2;

			}
			if(left(startx, starty)){
				flag=true;
				array[startx][starty]=0;
				starty--;
				array[startx][starty]=2;
				result++;
				direction[startx][starty]=1;
				if(Bingo()){
					if(result<min&&result!=0)
					{
						min=result;
					}
				}
				GuessGame();
				direction[startx][starty]=0;
				array[startx][starty]=0;
				starty++;
				result--;
				array[startx][starty]=2;

			}
			if(right(startx, starty)){
				flag=true;
				array[startx][starty]=0;
				starty++;
				result++;
				array[startx][starty]=2;
				direction[startx][starty]=1;
				if(Bingo()){
					if(result<min&&result!=0)
					{
						min=result;
					}
				}
				GuessGame();
				direction[startx][starty]=0;
				array[startx][starty]=0;
				starty--;
				result--;
				array[startx][starty]=2;

			}
			if(flag==false)
			{
				//System.out.println("back");
				return;
			}
	}	
	
	public void PrintArray(){
		for(int j=0;j<m;j++){
			for(int k=0;k<n;k++){
				System.out.print(array[j][k]+"\t");
			}
			System.out.println();
		}
	}
	public boolean Bingo(){
		if(startx==desx&&starty==desy){
			//System.out.println("Bingo:"+result);
			return true;	
		}else {
			return false;
		}
	}
	public static void main(String[] args) {
		BoxGame game = new BoxGame();
	}
}
