import numpy
import os
import random
import sys


#method to randomly generate matrices
def generateMatrix(nr_of_unknowns):
    m = numpy.eye(nr_of_unknowns)
    for i in range(nr_of_unknowns):
        if random.randint(0, 1) == 0:   # for diagonal elements
            m[i,i] = -1
        else:
            m[i,i] = 1

        for j in range(i+1,nr_of_unknowns):
            m[i,j] = random.randint(-3, 3)
    
    for j in range(nr_of_unknowns - 1, -1, -1):
        for i in range(j + i, nr_of_unknowns):
            if random.randint(0, 1) == 0:
                m[i] = fillValues(m, i, j, random.randint(-3, 1))
            else:
                m[i] = fillValues(m, i, j, random.randint(1, 4))

    
    swapValues(m,0,random.randint(1, nr_of_unknowns-1))
    
    n = numpy.zeros((nr_of_unknowns, 1))  #creating B matrix
    for i in range(nr_of_unknowns):
        n[i] = random.randint(-5,5)
    m_inverse = numpy.linalg.inv(m) # Compute the (multiplicative) inverse of a matrix
    x = numpy.dot(m_inverse, n)

    return m.astype(int), x.astype(int), n.astype(int)

#method to fill values 
def fillValues(m, i, j, value):
    return m[i] + value * m[j]

#method to randomly swap values
def swapValues(m, i, j):
    m[i] = m[i] + m[j]
    m[j] = m[i] - m[j]
    m[i] = m[i] - m[j]


def createSLE(nr_of_unknowns):
    if os.path.exists("GeneratedSleMatrix.tex"):
        os.remove("GeneratedSleMatrix.tex")
    infile = open("GeneratedSleMatrix.tex", "a")
    infile.write('\documentclass{article}\n')
    infile.write('\\begin{document}\n')
    infile.write('\\begin{enumerate}\n')
    infile.write('\item \n')

    a,x,b = generateMatrix(nr_of_unknowns)
    #print ("\nUnimodular matrix A:")
    #print (a)
    
    #print ("\nSolution matrix X:")
    #print (x)
    
    #print ("\nMatrix B:")
    #print (b)

    infile.write('$\\begin{array}{') 
    for i in range(nr_of_unknowns + 1):
        infile.write('r@{\ }c@{\ }')
    infile.write('}\n')

    #print("\nThe system of linear equations is: ")

    for i in range(nr_of_unknowns):
        text = ""
        for j in range(nr_of_unknowns):           
            if a[i, j] < 0:
                if j == 0:
                    token = '-'
                else:
                    token = ' -& '
                    
                if a[i, j] == -1:
                    k = ""
                else:
                     k = str(abs(a[i, j]))
                     
                l = k + 'x_{' + str(j+1) + '}&'
                                
            elif a[i, j] == 0:
                if j == 0:
                    token = ''
                    
                else:
                    token = '&'
                    
                l = '&'
            else:
                if j == 0:
                    token = ''
                else:
                    token = ' +& '
                    
                if a[i, j] == 1:
                    k = ""
                else:
                    k = str(abs(a[i, j]))
                    
                l = k + 'x_{' + str(j+1) + '}&'
                                
            text = text + token + l
            
            if j + 1 == nr_of_unknowns:
                text = text + '=&' + str(b[i, 0]) + ' \\\\\n '
                
        #print (text)
        infile.write(text)
    
    infile.write('\\end{array}$\n')
    infile.write('\end{enumerate}\n')
    infile.write('\end{document}\n')
    infile.close()

    #os.system("pdflatex GeneratedSleMatrix.tex")

    outfile = open("Solution.txt", "w")
    outfile.write("Solution matrix X:\n")
    outfile.write(str(x))
    outfile.close()

    outfile = open("Matrices.txt","w")
    outfile.write("Generated matrices:\n")
    outfile.write("Matrix A\n" + str(a) + "\n" + "Matrix B\n" + str(b))
    outfile.close()
    #os.system("GeneratedSleMatrix.pdf")


#main code
numberOfUnknowns = int(sys.argv[1])
createSLE(numberOfUnknowns)



