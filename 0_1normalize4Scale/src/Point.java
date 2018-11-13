import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created on 2018/11/12 10:10
 *
 * @author LY
 */
public class Point {

    /**
     * 线性归一化 公式：X(norm) = (X - min) / (max - min)
     *
     *
     * @return 归一化后的数据
     */

    public  static double Decimal(double d){
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double[][] normalize4Scale(double[][] points) {
        if (points == null || points.length < 1) {
            return points;
        }
        double[][] p = new double[points.length][points[0].length];
        double[] matrixJ;
        double maxV;
        double minV;
        for (int j = 0; j < points[0].length; j++) {
            matrixJ = getMatrixCol(points, j);
            maxV = maxV(matrixJ);
            minV = minV(matrixJ);
            for (int i = 0; i < points.length; i++) {
                p[i][j] = maxV == minV ? minV : (points[i][j] - minV) / (maxV - minV);
            }

        }
        return p;
    }

    /**
     * 获取矩阵的某一列
     *
     * @param points points
     * @param column column
     * @return double[]
     */
    public static double[] getMatrixCol(double[][] points, int column) {
        double[] matrixJ = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            matrixJ[i] = points[i][column];
        }
        return matrixJ;
    }

    /**
     * 获取数组中的最小值
     *
     * @param matrixJ matrixJ
     * @return v
     */
    public static double minV(double[] matrixJ) {
        double v = matrixJ[0];
        for (int i = 0; i < matrixJ.length; i++) {
            if (matrixJ[i] < v) {
                v = matrixJ[i];
            }
        }
        return v;
    }

    /**
     * 获取数组中的最大值
     *
     * @param matrixJ matrixJ
     * @return v
     */
    public static double maxV(double[] matrixJ) {
        double v = matrixJ[0];
        for (int i = 0; i < matrixJ.length; i++) {
            if (matrixJ[i] > v) {
                v = matrixJ[i];
            }
        }
        return v;
    }

    /**
     * 0均值\标准差归一化 公式：X(norm) = (X - μ) / σ
     * X(norm) = (X - 均值) / 标准差
     *
     * @param points 原始数据
     * @return 归一化后的数据
     */
    public static double[][] normalize4ZScore(double[][] points) {
        if (points == null || points.length < 1) {
            return points;
        }
        double[][] p = new double[points.length][points[0].length];
        double[] matrixJ;
        double avg;
        double std;
        for (int j = 0; j < points[0].length; j++) {
            matrixJ = getMatrixCol(points, j);
            avg = average(matrixJ);
            std = standardDeviation(matrixJ);
            for (int i = 0; i < points.length; i++) {
                p[i][j] = std == 0 ? points[i][j] : (points[i][j] - avg) / std;
            }
        }
        return p;
    }

    /**
     * 方差s^2=[(x1-x)^2 +...(xn-x)^2]/n
     *
     * @param x x
     * @return 方差
     */
    public static double variance(double[] x) {
        int m = x.length;
        double sum = 0;
        for (int i = 0; i < m; i++) {//求和
            sum += x[i];
        }
        double dAve = sum / m;//求平均值
        double dVar = 0;
        for (int i = 0; i < m; i++) {//求方差
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return dVar / m;
    }

    /**
     * 标准差σ=sqrt(s^2)
     *
     * @param x x
     * @return 标准差
     */
    public static double standardDeviation(double[] x) {
        return Math.sqrt(variance(x));
    }

    /**
     * 平均值
     *
     * @param x x
     * @return 平均值
     */
    public static double average(double[] x) {
        int m = x.length;
        double sum = 0;
        for (int i = 0; i < m; i++) {
            sum += x[i];
        }
        double dAve = sum / m;
        return dAve;
    }

    public static double[][] readfile(){
        DecimalFormat df   = new DecimalFormat("######0.00");
        // 初始化一个用于存储txt数据的数组
        int i=0,j=0;
        String[][] rows = new String[20][4];
        //初始化一个double二维数组
        double[][] doubles=new double[20][4];
        int index = 0;
        BufferedReader br = null;
        try {
            // 读文件了. 路径就是那个txt文件路径
            br  = new BufferedReader(new FileReader(new File("D:/football.txt")));
            String str = null;
            // 按行读取
            while((str=br.readLine())!=null){
                // 可能两个数字之间的空格数不固定,可以是n个.
                rows[index] = str.split("\\s+");
                index++;
            }
            // 打印出结果并转化为double二维数组
            for (String[] strings : rows) {
                for (String string : strings) {
                    doubles[i][j++]=Double.parseDouble(string);
                    System.out.print(string+"  ");
                }
                i++;
                j=0;
                System.out.println("\n");
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return doubles;
    }


    public static void main(String[] args) {
        double[][] points = readfile();
        double[][] p1 = normalize4Scale(points);
        for (int i=0;i<p1.length;i++){
            for (int j=0;j<p1[i].length;j++){
                System.out.print(p1[i][j]+" ");
            }
            System.out.println("\n");
        }

    }
}

