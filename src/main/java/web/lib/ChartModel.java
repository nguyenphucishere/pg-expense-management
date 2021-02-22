package web.lib;

import java.text.DecimalFormat;
import java.util.List;

import web.entity.Category;

public class ChartModel{
	
	public static String[] generateLabelAnDataToArrayForList(List<Object[]> dataInList) {
		String labelStr = "[";
		String dataStr = "[";
		List<Object[]> dataAndLabelInList = dataInList;
		
		DecimalFormat df = new DecimalFormat("###.###");
		
		for(Object[] data: dataAndLabelInList){
			
			labelStr += "\"" + data[0] + "\",";
//			Long longNum = null;
//			try {
//				longNum = (long)data[1];
//				dataStr += df.format(longNum)+ ",";
//			}catch(Exception e) {
				dataStr += df.format((double)data[1]) + ",";
			//}
			
//			Object o;
//			
//			if(longNum == null) {
//				o = data[1];
//			}else {
//				o = longNum;
//			}
			
		}
		
		labelStr = labelStr.substring(0, labelStr.length() - 1);
		dataStr = dataStr.substring(0, dataStr.length() - 1);
		
		labelStr += "]";
		dataStr += "]";
		
		String[] res = new String[2];
		res[0] = labelStr;
		res[1] = dataStr;
		
		return res;
	}
	
}