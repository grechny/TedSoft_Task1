package ie.globalcom;

public abstract class Toggle {

    public Toggle() {
        Boolean currentState = getToggleState();
        setToggleState(!currentState);
        System.out.println(this.getClass().getSimpleName() + ": I am turned "
                + (currentState ? "off" : "on") + "!");
    }

    private Boolean toggleState = false;

    protected void setToggleState(Boolean state){
        toggleState = state;
    }

    protected Boolean getToggleState(){
        return toggleState;
    }
}
