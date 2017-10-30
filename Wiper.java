package deep;

import java.util.Scanner;

abstract class abstractcontrolrod {// ������Ƹ���
	protected int maxlev;
	protected int lev;
	public abstractcontrolrod() {
		// TODO �Զ����ɵĹ��캯�����
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
		// TODO �Զ����ɵĹ��캯�����
	}
	public abstract void dospeed(int maxlev,int maxspeed,int maxdia);
	public abstract void show();
}

class controlrod extends abstractcontrolrod {
	public controlrod() {
		// TODO �Զ����ɵĹ��캯�����
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
		// TODO �Զ����ɵķ������
		if(c.lev==0){
			System.out.println("��ʱ���Ƹ�Ϊֹͣ״̬!!!");
		}else if(c.lev>=1){
			System.out.println("��ʱ���Ƹ�Ϊ��Ъ״̬!!!");
		}else if(c.lev==6){
			System.out.println("��ʱ���Ƹ�Ϊ����״̬!!!");
		}else if(c.lev==7){
			System.out.println("��ʱ���Ƹ�Ϊ����״̬!!!");
		}else if(c.lev>=8){
			System.out.println("��ʱ���Ƹ�Ϊ������״̬!!!");
		}
		System.out.println("�̶��̶̿�Ϊ:"+d.dia);
		if(b.speed==0){
			System.out.println("��ˢ�ٶ�Ϊ:0");
		}else if(b.speed==1){
			System.out.println("��ˢ�ٶ�Ϊ:4");
		}else if(b.speed==2){
			System.out.println("��ˢ�ٶ�Ϊ:6");
		}else if(b.speed==3){
			System.out.println("��ˢ�ٶ�Ϊ:12");
		}else if(b.speed==4){
			System.out.println("��ˢ�ٶ�Ϊ:18");
		}else if(b.speed==5){
			System.out.println("��ˢ�ٶ�Ϊ:24");
		}else if(b.speed==6){
			System.out.println("��ˢ�ٶ�Ϊ:30");
		}else if(b.speed==7){
			System.out.println("��ˢ�ٶ�Ϊ:60");
		}else{
			System.out.println("��ˢ�ٶ�Ϊ:90");
		}
	}
	@Override
	public void dospeed(int maxlev, int maxspeed, int maxdia) {
		// TODO �Զ����ɵķ������
		c.maxlev=maxlev;
		b.maxspeed=maxspeed;
		d.maxdia=maxdia;
	}
	
}

public class Wiper {
	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		merization m=new merization();
		System.out.println("��������Ƹ˵������:");
		int cmaxlev=input.nextInt();
		System.out.println("������̶��̵����̶�:");
		int cmaxspeed=input.nextInt();
		System.out.println("��������ˢ������ٶ���:");
		int cmaxdia=input.nextInt();
		m.dospeed(cmaxlev, cmaxspeed, cmaxdia);
		while(true){
			System.out.println("1.��ˢ����");
			System.out.println("2.��ˢ����");
			System.out.println("0.�˳�");
			System.out.println("��������Ҫѡ���ܵ����:");
			int a=input.nextInt();
			if(a==0){
				System.exit(0);
			}else if(a==1){
				if(m.c.lev+1<=m.c.maxlev){
					m.c.lev++;
					m.d.dia++;
					m.b.speed++;
				}else{
					System.out.println("�Ѿ�������󵵣������ټ��٣�����");
				}
				m.show();
			}else if(a==2){
				if(m.c.lev-1>=0){
					m.c.lev--;
					m.d.dia--;
					m.b.speed--;
				}else{
					System.out.println("��ˢ�Ѿ�ֹͣ���У������ټ��٣�����");
				}
				m.show();
			}
		}
	}
  }

