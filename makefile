#------------------------------------------------------------------------------
#  Makefile for CMPS 12B pa4
#------------------------------------------------------------------------------

JAVAC      = javac 
MAINCLASS  = Simulation
JAVASRC    = $(wildcard *.java)
SOURCES    = $(JAVASRC) makefile README
CLASSES    = $(patsubst %.java, %.class, $(JAVASRC))
JARCLASSES = $(patsubst %.class, %*.class, $(CLASSES))
JARFILE    = $(MAINCLASS) 


all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(JARCLASSES)
	chmod +x $(JARFILE)
	rm Manifest

%.class: %.java
	$(JAVAC) $<

clean:
	rm *.class $(JARFILE)

submit: 
	submit cmps012b-pt.u13 pa4 README makefile Simulation.java QueueInterface.java QueueEmptyException.java Queue.java QueueTest.java Job.java
