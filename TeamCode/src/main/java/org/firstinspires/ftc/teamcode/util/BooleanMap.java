package org.firstinspires.ftc.teamcode.util;

public class BooleanMap
{
    boolean[][] map;

    int x, y;

    public int[] max = new int[3];

    public BooleanMap(int x_size, int y_size)
    {
        x = x_size;
        y = y_size;
        map = new boolean[y][x];
    }

    public void set(boolean value, int x, int y)
    {
        map[y][x] = value;
    }

    public int[][] convert()
    {
        int[][] heatMap = new int[y][x];
        for(int i = 1; i < y - 1; i++)
        {
            for(int j = 1; j < x - 1; j++)
            {
                if(map[j+1][i])
                {
                    heatMap[j][i] = heatMap[j+1][i] + 1;
                }
                if(map[j-1][i])
                {
                    heatMap[j][i] = heatMap[j-1][i] + 1;
                }
                if(map[j][i+1])
                {
                    heatMap[j][i] = heatMap[j][i+1] + 1;
                }
                if(map[j][i-1])
                {
                    heatMap[j][i] = heatMap[j][i-1] + 1;
                }
                if(heatMap[j][i] > max[0])
                {
                    max[0] = heatMap[j][i];
                    max[1] = j;
                    max[2] = i;
                }
            }
        }
        return heatMap;
    }
}
