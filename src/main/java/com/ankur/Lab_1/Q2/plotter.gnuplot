set terminal pngcairo size 1200,600
set output 'barplot.png'

set title 'Probability Distribution'
set xlabel 'X'
set ylabel 'Probability'
set boxwidth 0.8
set style fill solid 1.0 border -1
set grid ytics
set xtics 5
set xrange [-50.5:60.5]

plot 'data.dat' using 1:2 with boxes notitle