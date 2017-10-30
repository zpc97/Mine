package deep;

import java.util.Scanner;

abstract class abstractcontrolrod {// 抽象控制杆类
	protected int maxlev;
	protected int lev;
	public abstractcontrolrod() {
		// TODO 自动生成的构造函数存根
		lev = 0;
	}
	public abstract int getlever();
	public abstract void leverup();
	public abstract void leverdown();
	public abstract int getmaxlever();
	public abstract void setmaxlev(int maxlev);
}

abstract class abstractbrush {
	protected int speed;
	protected int maxspeed;
	public abstractbrush() {
		speed = 0;
	}
	public abstract void setspeed(int speed);
	public abstract int getspeed();

}

abstract class abstractdial {
	protected int dia;
	protected int maxdia;
	public abstractdial() {
		dia = 0;
	}
	public abstract void setdial(int dia);
	public abstract int getdial();
	public abstract void dialup();
	public abstract void diadown();
}

abstract class abstractpolymerization {
	protected abstractcontrolrod contral;
	protected abstractbrush brush;
	protected abstractdial dial;
	public abstractpolymerization() {
		// TODO 自动生成的构造函数存根
	}
	public abstract void dospeed(int maxlev,int maxspeed,int maxdia);
	public abstract void show();
}

class controlrod extends abstractcontrolrod {
	public controlrod() {
		// TODO 自动生成的构造函数存根
		lev=0;
	}
	public int getlever() {
		return lev;
	}

	public void leverup() {
		lev++;
	}

	public void leverdown() {
		lev--;
	}

	public int getmaxlever() {
		return maxlev;
	}
	public void setmaxlev(int maxlev) {
		this.maxlev = maxlev;
	}

	
}

class brush extends abstractbrush {
	public brush() {
		speed = 0;
	}

	public void setspeed(int speed) {
		this.speed = speed;
	}

	public int getspeed() {
		return speed;
	}
}
class dial extends abstractdial {
	public dial() {
		dia = 0;
	}
	public void setdial(int dia) {
		this.dia = dia;
	}
	public int getdial() {
		return dia;
	}

	public void dialup() {
		dia++;
	}

	public void diadown() {
		dia--;
	}
}
class merization extends abstractpolymerization{
	controlrod c=new controlrod();
	brush b=new brush();
	dial d=new dial();
	public void show() {
		// TODO 自动生成的方法存根
		if(c.lev==0){
			System.out.println("此时控制杆为停止状态!!!");
		}else if(c.lev>=1){
			System.out.println("此时控制杆为间歇状态!!!");
		}else if(c.lev==6){
			System.out.println("此时控制杆为低速状态!!!");
		}else if(c.lev==7){
			System.out.println("此时控制杆为高速状态!!!");
		}else if(c.lev>=8){
			System.out.println("此时控制杆为超高速状态!!!");
		}
		System.out.println("刻度盘刻度为:"+d.dia);
		if(b.speed==0){
			System.out.println("雨刷速度为:0");
		}else if(b.speed==1){
			System.out.println("雨刷速度为:4");
		}else if(b.speed==2){
			System.out.println("雨刷速度为:6");
		}else if(b.speed==3){
			System.out.println("雨刷速度为:12");
		}else if(b.speed==4){
			System.out.println("雨刷速度为:18");
		}else if(b.speed==5){
			System.out.println("雨刷速度为:24");
		}else if(b.speed==6){
			System.out.println("雨刷速度为:30");
		}else if(b.speed==7){
			System.out.println("雨刷速度为:60");
		}else{
			System.out.println("雨刷速度为:90");
		}
	}
	@Override
	public void dospeed(int maxlev, int maxspeed, int maxdia) {
		// TODO 自动生成的方法存根
		c.maxlev=maxlev;
		b.maxspeed=maxspeed;
		d.maxdia=maxdia;
	}
	
}

public class Wiper {
	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		merization m=new merization();
		System.out.println("请输入控制杆的最大档数:");
		int cmaxlev=input.nextInt();
		System.out.println("请输入刻度盘的最大刻度:");
		int cmaxspeed=input.nextInt();
		System.out.println("请输入雨刷的最大速度数:");
		int cmaxdia=input.nextInt();
		m.dospeed(cmaxlev, cmaxspeed, cmaxdia);
		while(true){
			System.out.println("1.雨刷加速");
			System.out.println("2.雨刷减速");
			System.out.println("0.退出");
			System.out.println("请输入你要选择功能的序号:");
			int a=input.nextInt();
			if(a==0){
				System.exit(0);
			}else if(a==1){
				if(m.c.lev+1<=m.c.maxlev){
					m.c.lev++;
					m.d.dia++;
					m.b.speed++;
				}else{
					System.out.println("已经调至最大档，不能再加速！！！");
				}
				m.show();
			}else if(a==2){
				if(m.c.lev-1>=0){
					m.c.lev--;
					m.d.dia--;
					m.b.speed--;
				}else{
					System.out.println("雨刷已经停止运行，不能再减速！！！");
				}
				m.show();
			}
		}
	}
  }

