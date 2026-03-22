package org.nucastrodata.datastructure.util;

import java.util.*;


/**
 * The Class ReactionDataStructure.
 */
public class ReactionDataStructure{

	/**
	 * The Enum DecayType.
	 */
	public static enum DecayType{
		
		/** The NONE. */
		NONE("none")
		, 
 
 /** The BET a_ plus. */
 BETA_PLUS("bet+")
		, 
 
 /** The BET a_ minus. */
 BETA_MINUS("bet-")
		, 
 
 /** The EC. */
 EC("ec")
		, 
 
 /** The BEC. */
 BEC("bec")
		, 
 
 /** The BKMO. */
 BKMO("bkmo")
		, 
 
 /** The BTYK. */
 BTYK("btyk")
		, 
 
 /** The M o92. */
 MO92("mo92");
		
		/** The string. */
		private String string;
		
		/**
		 * Instantiates a new decay type.
		 *
		 * @param string the string
		 */
		DecayType(String string){this.string = string;}
		
		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		public String toString(){return string;}
		
		/**
		 * Gets the cGI name.
		 *
		 * @return the cGI name
		 */
		public String getCGIName(){
			if(toString().equals("none")){
				return "";
			}
			return toString();
		}
		
		/**
		 * Gets the decay type.
		 *
		 * @param string the string
		 * @return the decay type
		 */
		public static ReactionDataStructure.DecayType getDecayType(String string){
			if(string.equals("")){
				return ReactionDataStructure.DecayType.NONE;
			}else if(string.equals("bet+")){
				return ReactionDataStructure.DecayType.BETA_PLUS;
			}else if(string.equals("bet-")){
				return ReactionDataStructure.DecayType.BETA_MINUS;
			}else if(string.equals("ec")){
				return ReactionDataStructure.DecayType.EC;
			}else if(string.equals("bec")){
				return ReactionDataStructure.DecayType.BEC;
			}else if(string.equals("bkmo")){
				return ReactionDataStructure.DecayType.BKMO;
			}else if(string.equals("btyk")){
				return ReactionDataStructure.DecayType.BTYK;
			}else if(string.equals("mo92")){
				return ReactionDataStructure.DecayType.MO92;
			}
			return null;
		}
	}
	
	/** The reaction type. */
	private int reactionIndex, reactionType;
	
	/** The reaction name. */
	private String reactionName;
	
	/** The isotope point. */
	private IsotopePoint isotopePoint;
	
	/** The decay. */
	private DecayType decay;
	
	/**
	 * Instantiates a new reaction data structure.
	 */
	public ReactionDataStructure(){
		initialize();
	}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.eval.wizard.datastructure.CommentOwnerDataStructure#initialize()
	 */
	public void initialize(){
		setReactionIndex(0);
		setReactionName("");
		setReactionType(-1);
		setIsotopePoint(new IsotopePoint(0, 0));
		setDecay(DecayType.NONE);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ReactionDataStructure clone(){
		ReactionDataStructure rds = new ReactionDataStructure();
		rds.setReactionIndex(this.reactionIndex);
		rds.setReactionName(this.reactionName);
		rds.setReactionType(this.reactionType);
		rds.setIsotopePoint(this.isotopePoint);
		rds.setDecay(this.decay);
		return rds;
	}
	
	/**
	 * Gets the reaction index.
	 *
	 * @return the reaction index
	 */
	public int getReactionIndex(){return reactionIndex;}
	
	/**
	 * Sets the reaction index.
	 *
	 * @param reactionIndex the new reaction index
	 */
	public void setReactionIndex(int reactionIndex){
		this.reactionIndex = reactionIndex;
	}
	
	/**
	 * Gets the reaction type.
	 *
	 * @return the reaction type
	 */
	public int getReactionType(){return reactionType;}
	
	/**
	 * Sets the reaction type.
	 *
	 * @param reactionType the new reaction type
	 */
	public void setReactionType(int reactionType){this.reactionType = reactionType;}
	
	/**
	 * Gets the reaction name.
	 *
	 * @return the reaction name
	 */
	public String getReactionName(){return reactionName;}
	
	/**
	 * Sets the reaction name.
	 *
	 * @param reactionName the new reaction name
	 */
	public void setReactionName(String reactionName){this.reactionName = reactionName;}
	
	/**
	 * Gets the isotope point.
	 *
	 * @return the isotope point
	 */
	public IsotopePoint getIsotopePoint(){return isotopePoint;}
	
	/**
	 * Sets the isotope point.
	 *
	 * @param isotopePoint the new isotope point
	 */
	public void setIsotopePoint(IsotopePoint isotopePoint){this.isotopePoint = isotopePoint;}
	
	/**
	 * Gets the decay.
	 *
	 * @return the decay
	 */
	public DecayType getDecay(){return decay;}
	
	/**
	 * Sets the decay.
	 *
	 * @param decay the new decay
	 */
	public void setDecay(DecayType decay){this.decay = decay;}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.eval.wizard.datastructure.CommentOwnerDataStructure#toString()
	 */
	public String toString(){
		if(getDecay()==DecayType.NONE){
			return reactionName;
		}
		return reactionName + " [" + getDecay() + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}else if(!(obj instanceof ReactionDataStructure)){
			return false;
		}else{
			ReactionDataStructure rds = (ReactionDataStructure)obj;
			return this.getReactionIndex()==rds.getReactionIndex();
		}
	}
	
}
