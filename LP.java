package test;

import org.apache.commons.math3.exception.TooManyIterationsException;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Test linear programming
 */
public class LP {
    public static void main(String[] args){
        linearProgramCommons();
    }

    /**
     * Linear programming using Apache commons-math3-3.6.1
     * Example:
     * max.  3x1 + 5x2
     * s.t.
     * 2x1 + 8x2 <= 13
     * 5x1 - x2 <= 11
     * x1 >= 0, x2 >= 0
     */
    private static void linearProgramCommons(){
        LinearObjectiveFunction f = new LinearObjectiveFunction(new double[] {3, 5}, 0);

        //constraints
        Collection<LinearConstraint> constraints = new ArrayList<>();
        constraints.add(new LinearConstraint(new double[] { 2, 8}, Relationship.LEQ, 13));
        constraints.add(new LinearConstraint(new double[] { 5, -1}, Relationship.LEQ, 11));

        constraints.add(new LinearConstraint(new double[] { 1, 0}, Relationship.GEQ, 0));
        constraints.add(new LinearConstraint(new double[] { 0, 1}, Relationship.GEQ, 0));

        //create and run solver
        PointValuePair solution = null;
        try {
            solution = new SimplexSolver().optimize(f, new LinearConstraintSet(constraints), GoalType.MAXIMIZE);
        }
        catch (TooManyIterationsException e) {
            e.printStackTrace();
        }

        if (solution != null){
            //get solution
            double max = solution.getValue();
            System.out.println("Optimized production cost: " + max);

            //print decision variables
            for (int i = 0; i < 2; i++) {
                System.out.print(solution.getPoint()[i] + "\t");
            }
        }
    }
}
