package sunr.in.util;

/**
 * Created by NTCS on 2015-04-20.
 */

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sunr.in.R;

public class MainGridAdapter extends ArrayAdapter<Integer> {
    // 레이아웃 XML을 읽어들이기 위한 객체
    private LayoutInflater mInflater;
    Context mContext;

    public MainGridAdapter(Context context, ArrayList<Integer> object) {
        // 상위 클래스의 초기화 과정
        // context, 0, 자료구조
        super(context, 0, object);
        mContext = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // 보여지는 스타일을 자신이 만든 xml로 보이기 위한 구문
    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        View view = null;

        // 현재 리스트의 하나의 항목에 보일 컨트롤 얻기
        if (v == null) {
            // XML 레이아웃을 직접 읽어서 리스트뷰에 넣음
            view = mInflater.inflate(R.layout.main_grid_cont, parent,false);
            view.setTag(position);
        } else {
            view = v;
        }
        LinearLayout main_grid_lay = (LinearLayout) view.findViewById(R.id.main_grid_lay);
        LinearLayout main_grid_bottom = (LinearLayout) view.findViewById(R.id.main_grid_cont_bottom);
        TextView main_grid_cont_title = (TextView) view.findViewById(R.id.main_grid_cont_title);

        int[] colorsbg = mContext.getResources().getIntArray(R.array.grid_bg);
        int[] colors = mContext.getResources().getIntArray(R.array.grid_color);
        String[] titles = mContext.getResources().getStringArray(R.array.title_array);
        int[] imgs = {R.drawable.ic_grid_content4,R.drawable.ic_grid_content3,R.drawable.ic_grid_content2,R.drawable.ic_grid_content1,R.drawable.ic_grid_content5,R.drawable.ic_grid_content6};

        ((GradientDrawable)main_grid_lay.getBackground()).setColor(colorsbg[position]);
        ((GradientDrawable)main_grid_bottom.getBackground()).setColor(colors[position]);
        ImageView img = (ImageView) view.findViewById(R.id.main_grid_cont_img);
        img.setImageResource(imgs[position]);
        main_grid_cont_title.setText(titles[position]);
        return view;
    }
}
