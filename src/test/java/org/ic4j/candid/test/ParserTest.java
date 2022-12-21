package org.ic4j.candid.test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import org.ic4j.candid.parser.IDLParser;
import org.ic4j.candid.parser.IDLType;
import org.ic4j.candid.parser.tree.CandidParser;
import org.ic4j.candid.parser.tree.SimpleNode;
import org.ic4j.candid.types.Mode;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ParserTest {
	static Logger LOG;

	static final String IC_IDL_FILE = "ic.did";
	static final String IC_TEST_IDL_FILE = "ic_test.did";	
	static final String II_IDL_FILE = "internet_identity.did";
	static final String TRADING_IDL_FILE = "Trading.did";

	static {
		LOG = LoggerFactory.getLogger(ParserTest.class);
	}

	@Test
	public void test() {

		try {
			this.testIDL(TRADING_IDL_FILE);
			this.parseIDL(TRADING_IDL_FILE);
			this.testIDL(IC_TEST_IDL_FILE);
			this.parseIDL(IC_TEST_IDL_FILE);			
			this.testIDL(IC_IDL_FILE);
			this.parseIDL(IC_IDL_FILE);
			this.testIDL(II_IDL_FILE);
			this.parseIDL(II_IDL_FILE);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}

	}
	
	void parseIDL(String fileName) throws Exception {
		if (fileName != null) {
			LOG.info("Reading from file " + fileName);
			try {
				Reader reader = Files
						.newBufferedReader(Paths.get(getClass().getClassLoader().getResource(fileName).getPath()));
				IDLParser idlParser = new IDLParser(reader);
				idlParser.parse();
				
				Map<String,IDLType> types = idlParser.getTypes();
				
				for(String name : types.keySet())
				{
					LOG.info("IDL Type Name:" + name);
					
					IDLType type = types.get(name);
					LOG.info("Type Name:" + type.getName());
					LOG.info("Type:" + type.getType().name());
				}
				
				Map<String,IDLType> services = idlParser.getServices();
				
				Iterator<IDLType> it = services.values().iterator();
				
				while(it.hasNext())
				{
					Map<String,IDLType> meths = it.next().getMeths();
					
					for(String name : meths.keySet())
					{
						LOG.info("Meth Name:" + name);
						
						IDLType methType = meths.get(name);
						
						if(!methType.modes.isEmpty())
						{	
							if(methType.modes.get(0) == Mode.QUERY)
								LOG.info("is Query");
							else if(methType.modes.get(0) == Mode.ONEWAY)
								LOG.info("is Oneway");
						}
						
						for(IDLType type : methType.args)
						{
							LOG.info("Arg Name:" + type.getName());
							LOG.info("Arg Type:" + type.getType().name());
						}
						
						for(IDLType type : methType.rets)
						{
							LOG.info("Ret Name:" + type.getName());
							LOG.info("Ret Type:" + type.getType().name());
						}						
						
					}
				}
			} catch (IOException e) {
				throw e;
			}
		} else {
			throw new Exception("File " + fileName + " not found.");
		}

	}	

	void testIDL(String fileName) throws Exception {
		CandidParser parser;
		if (fileName != null) {
			LOG.info("Reading from file " + fileName);
			try {
				Reader reader = Files
						.newBufferedReader(Paths.get(getClass().getClassLoader().getResource(fileName).getPath()));
				parser = new CandidParser(reader);
			} catch (IOException e) {
				throw e;
			}
		} else {
			throw new Exception("File " + fileName + " not found.");
		}
		try {
			SimpleNode node = parser.Start();
			node.dump("candid");
			LOG.info("Done");

			this.parseNode(node);

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	void parseNode(SimpleNode node) {
		LOG.info(Integer.toString(node.jjtGetNumChildren()));
		LOG.info("Name:" + node.toString());

		if (node.jjtGetValue() != null)
			LOG.info("Value:" + node.jjtGetValue().toString());

		for (int i = 0; i < node.jjtGetNumChildren(); i++)
			this.parseNode((SimpleNode) node.jjtGetChild(i));

	}
}
