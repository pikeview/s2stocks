import org.crsh.cli.Command
import org.crsh.cli.Usage
import org.crsh.cli.Option
import org.crsh.command.InvocationContext
import java.util.List
import java.util.Map
import org.crsh.cli.Required
import org.crsh.command.InvocationContext
import org.springframework.beans.factory.BeanFactory

import exchange.gbc.models.Stock
import exchange.gbc.services.TradeQueryService
class peRatio {

    @Usage("Finds the P/E Ratio for a given stock at a given price")
    @Command
    def main(InvocationContext context,      
     		@Usage("the stock symbol") @Option(names=["s","stock"]) @Required String stock,
     		@Usage("the stock price")  @Option(names=["p","price"]) @Required String price     
     ) {
     
    	
    	Map<String,Object>  attributes= context.getAttributes();
    	BeanFactory beanFactory = (BeanFactory) attributes.get("spring.beanfactory");
		TradeQueryService tq = (TradeQueryService)beanFactory.getBean("tradeQueryService");
		
		//def tq =  context.attributes.beans["tradeQueryService"]
		StringBuilder sb = new StringBuilder();
		Double pe_ratio = tq.getStockPERatio( stock,  Double.parseDouble(price))
		sb.append("|").append("-"*68).append("|\r\n")
     	sb.append(String.format("| %20s | %20s | %20s |\r\n", "Stock","Price","Dividend Yield"))
     	sb.append("|").append("-"*22).append("|").append("-"*22).append("|").append("-"*22).append("|\r\n")
     	sb.append(String.format("| %20s | %20.7f | %20.7f |\r\n", stock,Double.parseDouble(price),pe_ratio))
     	sb.append("|").append("-"*68).append("|\r\n")
     	return new String(sb)
     
     }
}