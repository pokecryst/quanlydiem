package entity;

import helper.getID;

public class ClassStatus implements getID{
	
	private int clStatID;
	private String clStatName;
	
	public ClassStatus() {}
	
	public ClassStatus(int clStatID, String clStatName) {
		super();
		this.clStatID = clStatID;
		this.clStatName = clStatName;
	}
	
	@Override
    public int getID() {
        return clStatID;
    }

	public int getClStatID() {
		return clStatID;
	}

	public void setClStatID(int clStatID) {
		this.clStatID = clStatID;
	}

	public String getClStatName() {
		return clStatName;
	}

	public void setClStatName(String clStatName) {
		this.clStatName = clStatName;
	}

	@Override
	public String toString() {
		return clStatID + " - " + clStatName;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    ClassStatus other = (ClassStatus) obj;
	    return this.clStatID == other.clStatID;
	}

	@Override
	public int hashCode() {
	    return Integer.hashCode(clStatID);
	}
	
	
	
}
