package kr.android.grid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity {

	//�̹��� ������ ���� �迭(��ũ�ѻ�����ҷ����������)
	Integer[] images ={R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany, R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg, R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia, R.drawable.slovenia, R.drawable.spain, R.drawable.sweden,R.drawable.united_kingdom,R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany, R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg, R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia, R.drawable.slovenia, R.drawable.spain, R.drawable.sweden,R.drawable.united_kingdom,R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany, R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg, R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia, R.drawable.slovenia, R.drawable.spain, R.drawable.sweden,R.drawable.united_kingdom,R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany, R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg, R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia, R.drawable.slovenia, R.drawable.spain, R.drawable.sweden,R.drawable.united_kingdom};
	
	GridView gridView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		gridView = (GridView)findViewById(R.id.gridView1);
		
		//GridView�� �̹����� �����ֱ� ���� ImageAdapter��ü�� ���
		gridView.setAdapter(new ImageAdapter(this));//�ܺιɷ����� �����Ű�� ����
		
	}
	
	//BaseAdapter�� ����Ͽ� GridView�� �̹����� ������ �� �ִ� ��� ����(Customizing)
	public class ImageAdapter extends BaseAdapter{ //����Ŭ����(�߻�Ŭ����:�����ؾ��Ҹ޼�������->�ڵ��������Ȱ��)
		
		private Context context; //Context: Activity�� �θ� (App. ; ������Ʈ�� �ϳ��� Context(����)��.)
		
		public ImageAdapter(Context context){ //������ -> �ܺιɷ����� �����Ű�� ����
			this.context = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			//��ü �̹��� ����
			return images.length; // �������(images[])�� ���� 
		}
		
		//���޵� position���� ���� �ش� �̹����� ����
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return images[position];
		}
		
		//�̺�Ʈ�߻��� id�� ��ȯ (DB������ �����̸Ӹ�Ű �ε���???)
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		// TODO Auto-generated method stub
		//���� �̹����� ��ȯ(�̹��� ������ŭ �ݺ�)
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			               //��ġ��,    ǥ�õ� �̹���View,     �θ���View (GridView)
			
			//������ �� �ִ� ImageView�� �ִٸ� ImageView�� ��Ȱ���ϰ� 
			//������ ImageView�� ������ ImageView��ü �����۾� ����
			ImageView imageView = (ImageView)convertView; //��Ȱ��
			if(convertView == null){//�����Ǿ� ��Ȱ���� ImageView�� ������
				imageView = new ImageView(context);
			}
			//ImageView�� ũ��� �̹��� ũ��, �׸��� ����(padding) ����
			imageView.setLayoutParams(new GridView.LayoutParams(120,120));//�̹���View�� ����,���� ����
			imageView.setPadding(10, 10, 10, 10);
			//�̹����� ���ڷ� �Ѿ�� position�� �°� ����
			imageView.setImageResource(images[position]);
			
			return imageView;//���� �̹����� ���� �׸���信 ��ȯ�Ǿ� ���÷��̵�
		}
		
	}
	

}
