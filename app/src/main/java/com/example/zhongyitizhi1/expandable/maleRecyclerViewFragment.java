package com.example.zhongyitizhi1.expandable;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhongyitizhi1.expandable.ExpandableLayout;
import com.example.zhongyitizhi1.R;
import com.google.android.material.tabs.TabLayout;

//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;


public class maleRecyclerViewFragment extends Fragment {
    public static final int QUESTIONSUM = 64;
    private TextView textpeople;
    private LinearLayoutCompat l1;
    private ImageView l2;
    public  static int questionindex[] = new int[]{
        R.string.question1,R.string.question2,R.string.question3,R.string.question4,R.string.question5,R.string.question6,R.string.question7,R.string.question8,
        R.string.question9,R.string.question10,R.string.question11,R.string.question12,R.string.question13,R.string.question14,R.string.question15,R.string.question16,
        R.string.question17,R.string.question18,R.string.question19,R.string.question20,R.string.question21,R.string.question22,R.string.question23,R.string.question24,
        R.string.question25,R.string.question26,R.string.question27,R.string.question28,R.string.question29,R.string.question30,R.string.question31,R.string.question32,
        R.string.question33,R.string.question34,R.string.question35,R.string.question36,R.string.question37,R.string.question38,R.string.question39,R.string.question40,
        R.string.question41,R.string.question42,R.string.question43male,R.string.question44,R.string.question45,R.string.question46,R.string.question47,R.string.question48,
        R.string.question49,R.string.question50,R.string.question51,R.string.question52,R.string.question53,R.string.question54,R.string.question55,R.string.question56,
        R.string.question57,R.string.question58,R.string.question59,R.string.question60,R.string.question61,R.string.question62,R.string.question63,R.string.question64
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_fragment, container, false);//装载layout
        textpeople = rootView.findViewById(R.id.peopletext);
        l1 = rootView.findViewById(R.id.linearInExpandable);
        l2 = rootView.findViewById(R.id.people);
        textpeople.setText("这是一份为男性精心准备的调查问卷，以下问题请您根据最近三个月的身体感受回答，但是不建议16岁以下孩童回答");
        l1.setBackgroundResource(R.drawable.yuanjiaojuzhen5);
        l2.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_nansheng));
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SimpleAdapter(recyclerView));

        return rootView;
    }

    private static class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
        private static final int UNSELECTED = -1;

        private RecyclerView recyclerView;
        private int selectedItem = UNSELECTED;

        public SimpleAdapter(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind();
        }

        @Override
        public int getItemCount() {
            return QUESTIONSUM;
        }

        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {
            private ExpandableLayout expandableLayout;
            private TextView expandButton;
            //五个选项，这里没有选择使用radio group，我觉得不太好用，样式更改也比较麻烦
            private Button c1;
            private Button c2;
            private Button c3;
            private Button c4;
            private Button c5;
            //用于存储问题回答的数组，单选就是12345，多选就用多位数存储
            private int question[];
            public ViewHolder(View itemView) {
                super(itemView);

                expandableLayout = itemView.findViewById(R.id.expandable_layout);
                expandableLayout.setInterpolator(new OvershootInterpolator());
                expandableLayout.setOnExpansionUpdateListener(this);
                expandButton = itemView.findViewById(R.id.expand_button);
                c1 = itemView.findViewById(R.id.choose1);
                c2 = itemView.findViewById(R.id.choose2);
                c3 = itemView.findViewById(R.id.choose3);
                c4 = itemView.findViewById(R.id.choose4);
                c5 = itemView.findViewById(R.id.choose5);
                c1.setOnClickListener(mListener);
                c2.setOnClickListener(mListener);
                c3.setOnClickListener(mListener);
                c4.setOnClickListener(mListener);
                c4.setOnClickListener(mListener);
                c5.setOnClickListener(mListener);
                question = new int[QUESTIONSUM];//创建一个大小为10的数组，用来存储各个问题的回答
                initquestion(question);//初始化数组全部设为0
                expandButton.setOnClickListener(this);
            }
            View.OnClickListener mListener = new View.OnClickListener() {
                int position = getAdapterPosition();

                //不同按钮按下的监听事件选择，现在主要是更改颜色，如果要补充效果，可以在这里补充
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.choose1:
                            if (question[selectedItem] != 1) {
                                changecolor(question[selectedItem]);
                                c1.setBackgroundResource(R.drawable.circle_green);
                                c1.setTextColor(Color.parseColor("#ffffff"));
                                question[selectedItem] = 1;
                            }
                            expandButton.setTextColor(Color.parseColor("#2ebf91"));
                            break;
                        case R.id.choose2:
                            if (question[selectedItem] != 2) {
                                changecolor(question[selectedItem]);
                                c2.setBackgroundResource(R.drawable.circle_green);
                                c2.setTextColor(Color.parseColor("#ffffff"));
                                question[selectedItem] = 2;
                            }
                            expandButton.setTextColor(Color.parseColor("#2ebf91"));
                            break;
                        case R.id.choose3:
                            if (question[selectedItem] != 3) {
                                changecolor(question[selectedItem]);
                                c3.setBackgroundResource(R.drawable.circle_green);
                                c3.setTextColor(Color.parseColor("#ffffff"));
                                question[selectedItem] = 3;
                            }
                            expandButton.setTextColor(Color.parseColor("#2ebf91"));
                            break;
                        case R.id.choose4:
                            if (question[selectedItem] != 4) {
                                changecolor(question[selectedItem]);
                                c4.setBackgroundResource(R.drawable.circle_green);
                                c4.setTextColor(Color.parseColor("#ffffff"));
                                question[selectedItem] = 4;
                            }
                            expandButton.setTextColor(Color.parseColor("#2ebf91"));
                            break;
                        case R.id.choose5:
                            if (question[selectedItem] != 5) {
                                changecolor(question[selectedItem]);
                                c5.setBackgroundResource(R.drawable.circle_green);
                                c5.setTextColor(Color.parseColor("#ffffff"));
                                question[selectedItem] = 5;
                            }
                            expandButton.setTextColor(Color.parseColor("#2ebf91"));
                            break;
                    }
                }
            };
//在点击一个新的选项之后，更改上一个选项的颜色
            public void changecolor(int i) {
                switch (i) {
                    case 1:
                        c1.setBackgroundResource(R.drawable.circle_white);
                        c1.setTextColor(Color.parseColor("#000000"));
                    case 2:
                        c2.setBackgroundResource(R.drawable.circle_white);
                        c2.setTextColor(Color.parseColor("#000000"));
                    case 3:
                        c3.setBackgroundResource(R.drawable.circle_white);
                        c3.setTextColor(Color.parseColor("#000000"));
                    case 4:
                        c4.setBackgroundResource(R.drawable.circle_white);
                        c4.setTextColor(Color.parseColor("#000000"));
                    case 5:
                        c5.setBackgroundResource(R.drawable.circle_white);
                        c5.setTextColor(Color.parseColor("#000000"));
                }
            }

            public void initquestion(int[] a) {
                for (int i = 0; i < a.length; i++) {
                    a[i] = 0;
                }
            }
//这里是初始化组件绑定的地方
            public void bind() {
                int position = getAdapterPosition();
                boolean isSelected = position == selectedItem;
                //为五个选型设定文字，如果有的问题不是这几个选项，到下面switch中单独设定就好
                c1.setText(R.string.option1);
                c2.setText(R.string.option2);
                c3.setText(R.string.option3);
                c4.setText(R.string.option4);
                c5.setText(R.string.option5);
                //为每个问题单独设定问题，其实可以建一个类，然后绑定，但是想想好像也不会简便很多
                expandButton.setText(questionindex[position]);
//                switch (position) {
//                    case 0:
//                        expandButton.setText(R.string.question1);
//                        break;
//                    case 1:
//                        expandButton.setText(R.string.question2);
//                        break;
//                    case 2:
//                        expandButton.setText(R.string.question3);
//                        break;
//                    case 3:
//                        expandButton.setText(R.string.question4);
//                        break;
//                    case 4:
//                        expandButton.setText(R.string.question5);
//                        break;
//                    case 5:
//                        expandButton.setText(R.string.question6);
//                        break;
//                    case 6:
//                        expandButton.setText("7.你面部两颧潮红或偏红吗？");
//                        break;
//                    case 7:
//                        expandButton.setText("8.你两颧有细微红丝吗？");
//                        break;
//                    case 8:
//                        expandButton.setText("9.你腹部肥满松软吗？");
//                        break;
//                    case 9:
//                        expandButton.setText("10.你有额头油脂分泌多的现象吗");
//                        break;
//                }
                expandButton.setSelected(isSelected);
                expandableLayout.setExpanded(isSelected, false);
            }
//点击expandbutton的效果
            @Override
            public void onClick(View view) {
                ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
                if (holder != null) {
                    holder.expandButton.setSelected(false);
                    holder.expandableLayout.collapse();
                }

                int position = getAdapterPosition();
                if (position == selectedItem) {
                    selectedItem = UNSELECTED;
                } else {
                    expandButton.setSelected(true);
                    expandableLayout.expand();
                    selectedItem = position;
                    //如果当前问题没有回答（默认为0），就把问题颜色设为红，选择之后设为绿
                    if (question[position] == 0) {
                        expandButton.setTextColor(Color.parseColor("#F37335"));
                    }
                }
            }

            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout", "State: " + state);
                if (state == ExpandableLayout.State.EXPANDING) {
                    recyclerView.smoothScrollToPosition(getAdapterPosition());
                }
            }
        }
    }
}
