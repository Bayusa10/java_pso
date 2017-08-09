/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcpso;
import java.util.Scanner;
/**
 *
 * @author Bayu
 */
public class rcpso_main {
    public static void main(String[] args) {
        Rcpso rcpso = new Rcpso();
        Scanner input = new Scanner(System.in);
        System.out.println("================================================");
        System.out.println("== Implementation of Real Coded PSO Algorithm ==");
        System.out.println("================================================");
        
        System.out.print("Input The Number of Particle: ");
        int PopSize = input.nextInt();
        System.out.println();
        
        System.out.print("Input The Number of W (range in [0.1;0.9]): ");
        double W = input.nextFloat();
        System.out.println();
        
        System.out.print("Input The Number of c1: ");
        double c1 = input.nextFloat();
        System.out.println();
        
        System.out.print("Input The Number of c2: ");
        double c2 = input.nextFloat();
        System.out.println();
        
        System.out.print("Input Maximum Iteration: ");
        int max_iteration = input.nextInt();
        System.out.println();
        
        double r1 = Math.random();
        double r2 = Math.random();
        
        System.out.println();
        System.out.println("==========================");
        System.out.println("== Initialilation Phase ==");
        System.out.println("==========================");
        
        System.out.println();
        System.out.println("========================================");
        System.out.println("== Initialitation of Particle's Speed ==");
        System.out.println("========================================");
        System.out.println();
        
        rcpso.Init_Speed(PopSize);
        double particle_speed[][] = rcpso.getParticle_Speed();
        for (int i=0; i < particle_speed.length; i++){
            for (int j=0; j < particle_speed[0].length; j++){
                if (j==0){
                     System.out.print("Particle-"+(i+1)+" "+particle_speed[i][j]+" ");
                } else{
                    System.out.print(particle_speed[i][j]+" ");
                } 
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println("===========================================");
        System.out.println("== Initialitation of Particle's Position ==");
        System.out.println("===========================================");
        System.out.println();
        
        rcpso.Init_Pos_Particle(PopSize);
        double particle_position[][] = rcpso.getParticle_Position();
        rcpso.Calculate_Fitness(particle_position);
        double fitness_particle[] = rcpso.getFitness_Particle();
        
         for (int i=0; i < particle_position.length; i++){
            for (int j=0; j < particle_position[0].length; j++){
                if (j==0){
                     System.out.print("Particle-"+(i+1)+" "+particle_position[i][j]+"     ");
                } else{
                    System.out.print(particle_position[i][j]+"     "+"Fitness: "+fitness_particle[i]);
                } 
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println("=============================");
        System.out.println("== Initiate PBest Particle ==");
        System.out.println("=============================");
        System.out.println();
        
        rcpso.Init_PBest_Particle(particle_position, fitness_particle);
        double pbest_particle[][] = rcpso.getPBest();
        double fitness_pbest[] = rcpso.getFitness_PBest();
        for (int i=0; i < pbest_particle.length; i++){
            for (int j=0; j < pbest_particle[0].length; j++){
                 if (j==0){
                     System.out.print("PBest-"+(i+1)+" "+pbest_particle[i][j]+"     ");
                } else{
                    System.out.print(pbest_particle[i][j]+"     "+"Fitness: "+fitness_pbest[i]);
                } 
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println("=============================");
        System.out.println("== Initiate GBest Particle ==");
        System.out.println("=============================");
        System.out.println();
        
        rcpso.Init_GBest(pbest_particle, fitness_pbest);
        double GBest[] = rcpso.getGBest();
        double fitness_gbest = rcpso.getFitness_GBest();
        for (int i=0; i < GBest.length; i++){
            if (i < GBest.length-1){
                System.out.print(GBest[i] + "  ");
            } else{
                System.out.println(GBest[i] +"   "+"Fitness: "+fitness_gbest);
            }
        }
        
        System.out.println();
        System.out.println("=================================");
        System.out.println("== Updated of Particle's Speed ==");
        System.out.println("=================================");
        System.out.println();
        
        rcpso.Calculate_Limit();
        rcpso.Speed_Update(particle_position, pbest_particle, GBest, W, c1, c2, r1, r2);
        particle_speed = rcpso.getParticle_Speed();
        for (int i=0; i < particle_speed.length; i++){
            for (int j=0; j < particle_speed[0].length; j++){
                if (j==0){
                     System.out.print("Particle-"+(i+1)+" "+particle_speed[i][j]+" ");
                } else{
                    System.out.print(particle_speed[i][j]+" ");
                } 
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println("====================================");
        System.out.println("== Updated of Particle's Position ==");
        System.out.println("====================================");
        System.out.println();
        
        rcpso.Update_Position(particle_speed);
        particle_position = rcpso.getParticle_Position();
        rcpso.Calculate_Fitness(particle_position);
        fitness_particle = rcpso.getFitness_Particle();
        
         for (int i=0; i < particle_position.length; i++){
            for (int j=0; j < particle_position[0].length; j++){
                if (j==0){
                     System.out.print("Particle-"+(i+1)+" "+particle_position[i][j]+"     ");
                } else{
                    System.out.print(particle_position[i][j]+"     "+"Fitness: "+fitness_particle[i]);
                } 
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println("=============================");
        System.out.println("== Updated PBest Particle ==");
        System.out.println("=============================");
        System.out.println();
        
        rcpso.Update_PBest_Particle(pbest_particle, fitness_pbest, particle_position, fitness_particle);
        pbest_particle = rcpso.getPBest();
        fitness_pbest = rcpso.getFitness_PBest();
        for (int i=0; i < pbest_particle.length; i++){
            for (int j=0; j < pbest_particle[0].length; j++){
                 if (j==0){
                     System.out.print("PBest-"+(i+1)+" "+pbest_particle[i][j]+"     ");
                } else{
                    System.out.print(pbest_particle[i][j]+"     "+"Fitness: "+fitness_pbest[i]);
                } 
            }
            System.out.println();
        }
         
         
        System.out.println();
        System.out.println("=============================");
        System.out.println("== Updated GBest Particle ==");
        System.out.println("=============================");
        System.out.println();
        
        rcpso.Update_GBest(pbest_particle, fitness_pbest);
        GBest = rcpso.getGBest();
        fitness_gbest = rcpso.getFitness_GBest();
        for (int i=0; i < GBest.length; i++){
            if (i < GBest.length-1){
                System.out.print(GBest[i] + "  ");
            } else{
                System.out.println(GBest[i] +"   "+"Fitness: "+fitness_gbest);
            }
        }
        
        
        System.out.println();
        System.out.println("=====================");
        System.out.println("== Iteration Phase ==");
        System.out.println("=====================");
        
        for (int iteration=1; iteration < max_iteration;iteration++){
        
        System.out.println();
        System.out.println("=====================");
        System.out.println("== Iteration "+(iteration)+" ==");
        System.out.println("=====================");
           
        System.out.println();
        System.out.println("=================================");
        System.out.println("== Updated of Particle's Speed ==");
        System.out.println("=================================");
        System.out.println();
        
        rcpso.Speed_Update(particle_position, pbest_particle, GBest, W, c1, c2, r1, r2);
        particle_speed = rcpso.getParticle_Speed();
        for (int i=0; i < particle_speed.length; i++){
            for (int j=0; j < particle_speed[0].length; j++){
                if (j==0){
                     System.out.print("Particle-"+(i+1)+" "+particle_speed[i][j]+" ");
                } else{
                    System.out.print(particle_speed[i][j]+" ");
                } 
            }
            System.out.println();
        }
            
        System.out.println();
        System.out.println("====================================");
        System.out.println("== Updated of Particle's Position ==");
        System.out.println("====================================");
        System.out.println();
        
        rcpso.Update_Position(particle_speed);
        particle_position = rcpso.getParticle_Position();
        rcpso.Calculate_Fitness(particle_position);
        fitness_particle = rcpso.getFitness_Particle();
        
         for (int i=0; i < particle_position.length; i++){
            for (int j=0; j < particle_position[0].length; j++){
                if (j==0){
                     System.out.print("Particle-"+(i+1)+" "+particle_position[i][j]+"     ");
                } else{
                    System.out.print(particle_position[i][j]+"     "+"Fitness: "+fitness_particle[i]);
                } 
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println("=============================");
        System.out.println("== Updated PBest Particle ==");
        System.out.println("=============================");
        System.out.println();
        
        rcpso.Update_PBest_Particle(pbest_particle, fitness_pbest, particle_position, fitness_particle);
        pbest_particle = rcpso.getPBest();
        fitness_pbest = rcpso.getFitness_PBest();
        for (int i=0; i < pbest_particle.length; i++){
            for (int j=0; j < pbest_particle[0].length; j++){
                 if (j==0){
                     System.out.print("PBest-"+(i+1)+" "+pbest_particle[i][j]+"     ");
                } else{
                    System.out.print(pbest_particle[i][j]+"     "+"Fitness: "+fitness_pbest[i]);
                } 
            }
            System.out.println();
        }
         
         
        System.out.println();
        System.out.println("=============================");
        System.out.println("== Updated GBest Particle ==");
        System.out.println("=============================");
        System.out.println();
        
        rcpso.Update_GBest(pbest_particle, fitness_pbest);
        GBest = rcpso.getGBest();
        fitness_gbest = rcpso.getFitness_GBest();
        for (int i=0; i < GBest.length; i++){
            if (i < GBest.length-1){
                System.out.print(GBest[i] + "  ");
            } else{
                System.out.println(GBest[i] +"   "+"Fitness: "+fitness_gbest);
            }
        }
            
            
            
            
        }
        
    }
    
}
