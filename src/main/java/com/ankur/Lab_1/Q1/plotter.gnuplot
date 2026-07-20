set terminal pngcairo size 900,600
set output "head_tail_plot.png"

set title "Head and Tail Arrays"
set xlabel "Index"
set ylabel "Value"

set grid
set key top left

plot \
    "data.dat" using 1:2 with linespoints lw 2 pt 7 title "Head", \
    "data.dat" using 1:3 with linespoints lw 2 pt 5 title "Tail"