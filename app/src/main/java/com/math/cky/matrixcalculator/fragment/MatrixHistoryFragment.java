package com.math.cky.matrixcalculator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.adapter.AnimationListAdapter;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.adapter.ScaleInAnimatorAdapter;

/**
 * Created by chen on 2017/6/11.
 */
public class MatrixHistoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    protected AnimationListAdapter mAdapter;
    private List<String> timeList;
    private List<String> infoList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.matrix_history_laout,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.matrix_history_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AnimationListAdapter(getActivity(),timeList,infoList);
        ScaleInAnimatorAdapter scaleInRecyclerViewAnimationAdapter = new ScaleInAnimatorAdapter(mAdapter, mRecyclerView);
        mRecyclerView.setAdapter(scaleInRecyclerViewAnimationAdapter);
        return view;
    }
    private void initdata(){
        timeList=new ArrayList();
        infoList=new ArrayList();
        timeList.add("史前年代");
        timeList.add("东汉前期");
        timeList.add("1683年");
        timeList.add("1750年");
        timeList.add("1800年代");
        timeList.add("1844年");
        timeList.add("1850年");
        timeList.add("从1858年开始");
        timeList.add("1878年");
        timeList.add("1879年");
        timeList.add("1884年");
        timeList.add("1906年");
        timeList.add("1922年");
        timeList.add("1925年");
        timeList.add("1935年");
        timeList.add("1993年");

        infoList.add("矩阵的研究历史悠久，拉丁方阵和幻方在史前年代已有人研究。");
        infoList.add("成书最迟在东汉前期的《九章算术》中，用分离系数法表示线性方程组，得到了其增广矩阵。在消元过程中，使用的把某行乘以某一非零实数、从某行中减去另一行等运算技巧，相当于矩阵的初等变换。但那时并没有现今理解的矩阵概念，虽然它与现有的矩阵形式上相同，但在当时只是作为线性方程组的标准表示与处理方式。");
        infoList.add("日本数学家关孝和（1683年）与微积分的发现者之一戈特弗里德·威廉·莱布尼茨（1693年）近乎同时地独立建立了行列式论。其后行列式作为解线性方程组的工具逐步发展。");
        infoList.add("加布里尔·克拉默发现了克莱姆法则。");
        infoList.add("矩阵的现代概念在19世纪逐渐形成。1800年代，高斯和威廉·若尔当建立了高斯—若尔当消去法。");
        infoList.add("1844年，德国数学家费迪南·艾森斯坦（F.Eisenstein）讨论了“变换”（矩阵）及其乘积。");
        infoList.add("英国数学家詹姆斯·约瑟夫·西尔维斯特（James Joseph Sylvester）首先使用矩阵一词。");
        infoList.add("英国数学家阿瑟·凯利发表了《矩阵论的研究报告》等一系列关于矩阵的专门论文，研究了矩阵的运算律、矩阵的逆以及转置和特征多项式方程。凯利还提出了凯莱-哈密尔顿定理，并验证了3×3矩阵的情况，又说进一步的证明是不必要的。");
        infoList.add("1854年时法国数学家埃尔米特（C.Hermite）使用了“正交矩阵”这一术语，但他的正式定义直到1878年才由费罗贝尼乌斯发表。");
        infoList.add("费罗贝尼乌斯引入矩阵秩的概念。至此，矩阵的体系基本上建立起来了。");
        infoList.add("庞加莱在两篇不严谨地使用了无限维矩阵和行列式理论的文章后开始了对无限维矩阵的专门研究。");
        infoList.add("希尔伯特引入无限二次型（相当于无限维矩阵）对积分方程进行研究，极大地促进了无限维矩阵的研究。在此基础上，施密茨、赫林格和特普利茨发展出算子理论，而无限维矩阵成为了研究函数空间算子的有力工具。");
        infoList.add("程廷熙在一篇介绍文章中将矩阵译为“纵横阵”。");
        infoList.add("科学名词审查会算学名词审查组在《科学》第十卷第四期刊登的审定名词表中，矩阵被翻译为“矩阵式”，方块矩阵翻译为“方阵式”，而各类矩阵如“正交矩阵”、“伴随矩阵”中的“矩阵”则被翻译为“方阵”。");
        infoList.add("中国数学会审查后，中华民国教育部审定的《数学名词》（并“通令全国各院校一律遵用，以昭划一”）中，“矩阵”作为译名首次出现。");
        infoList.add("中国自然科学名词审定委员会公布的《数学名词》中，“矩阵”被定为正式译名，并沿用至今。");

    }
}
