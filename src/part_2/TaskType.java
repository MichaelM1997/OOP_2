package part_2;

public enum TaskType {
    COMPUTATIONAL(1){
        @Override
        public String toString(){
            return "Computational part_2.Task";
        }
    },
    IO(2){
        @Override
        public String toString(){
            return "IO-Bound part_2.Task";
        }
    },
    OTHER(3){
        @Override
        public String toString(){
            return "Unknown part_2.Task";
        }
    };
    private int typePriority;
    private TaskType(int priority){
        if (validatePriority(priority))
            typePriority = priority;
        else
            throw new IllegalArgumentException("Priority is not an integer");
    }
    public void setPriority(int priority){
        if(validatePriority(priority))
            this.typePriority = priority;
        else
            throw new IllegalArgumentException("Priority is not an integer");
    }


    public int getPriorityValue(){
        return typePriority;
    }
    public TaskType getType(){
        return this;
    }
    /**
     * priority is represented by an integer value, ranging from 1 to 10
     * @param priority
     * @return whether the priority is valid or not
     */
    private static boolean validatePriority(int priority){

        return priority >= 1 && priority <= 10;
    }
}