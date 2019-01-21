package persistence.loader.tabDataSet;

/**
 * �������� �������� ������ �������: ������ ���������� �����. ��� ���� ��������� ��������, 
 * ��������� � ���������� ��������������� ��������� � ��������������� ��������
 */

import persistence.loader.DataSet;

public class RowLineroute extends RowIdNameDescription {

	public RowLineroute(int id, String name, int numberWork, int inputBufferMin,int inputBuffer,int inputBufferMax,int outputBufferMin,int outputBuffer,int outputBufferMax,String description)	{	
		super(id, name, description);
			
		this.numberWork		=	numberWork;			
		this.inputBufferMin	=	inputBufferMin;		
		this.inputBuffer	=	inputBuffer;
		this.inputBufferMax	=	inputBufferMax;		
		this.outputBufferMin=	inputBufferMin;		
		this.outputBuffer	=	inputBuffer;
		this.outputBufferMax=	inputBufferMax;	
	}	
	public RowLineroute()											{											}	
	public RowLineroute(DataSet dataSet, Class cL)					{	super(dataSet, cL);						}
	

    public int getInputBufferMin()								    {   return inputBufferMin;					}
    public void setInputBufferMin(int inputBufferMin)			    {   this.inputBufferMin = inputBufferMin;   }
    public int getInputBuffer()									    {   return inputBuffer;						}
    public void setInputBuffer(int inputBuffer)						{   this.inputBuffer = inputBuffer;			}
    public int getInputBufferMax()								    {   return inputBufferMax;				    }
    public void setInputBufferMax(int inputBufferMax)			    {   this.inputBufferMax = inputBufferMax;   }
    public int getOutputBufferMin()									{   return outputBufferMin;				    }
    public void setOutputBufferMin(int outputBufferMin)			    {   this.outputBufferMin = outputBufferMin; }
	public int getOutputBuffer()								    {   return outputBuffer;				    }
	public void setOutputBuffer(int outputBuffer)				    {   this.outputBuffer = outputBuffer;	    }
	public int getOutputBufferMax()								    {   return outputBufferMax;				    }
	public void setOutputBufferMax(int outputBufferMax)			    {   this.outputBufferMax = outputBufferMax; }
	public int getNumberWork()									    {   return numberWork;					    }
	public void setNumberWork(int numberWork)					    {   this.numberWork = numberWork;			}


	/** ���������� ����� ���������� ��������������� ��������			*/ 		
	private int numberWork	=	0;
	/** ����������� ������� �������� ������								*/ 		
	private int inputBufferMin	=	0;
	/** ������� ������� �������� ������									*/ 		
	private int inputBuffer	=	0;	
	/** ������������ ������� �������� ������							*/ 				
	private int inputBufferMax 	=	Integer.MAX_VALUE;
	/** ����������� ������� ��������� ������							*/ 		
	private int outputBufferMin	=	0;
	/** ������� ������� ��������� ������								*/ 		
	private int outputBuffer	=	0;	
	/** ������������ ������� ��������� ������							*/	
	private int outputBufferMax = 	Integer.MAX_VALUE;
	
}
