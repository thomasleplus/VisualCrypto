# $Id: Makefile,v 1.6 2003/03/28 00:41:11 leplusth Exp $

all: rapport.dvi rapport.ps rapport.pdf

rapport.dvi: rapport.tex
	latex rapport
	bibtex rapport
	latex rapport
	latex rapport

rapport.ps: rapport.dvi
	dvips rapport.dvi -o rapport.ps

rapport.pdf: rapport.ps
	ps2pdf rapport.ps

clean:
	rm -Rf html *.aux *.log *.lof *.toc *.bbl *.blg core* *~