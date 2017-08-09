/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcpso;
import java.util.Random;
/**
 *
 * @author Bayu
 */
public class Rcpso {
    double Particle_Speed[][];
    double Particle_Position[][];
    double Fitness_Particle[];
    double PBest[][];
    double Fitness_PBest[];
    double GBest[];
    double Fitness_GBest;
    double x1_min = -5.0;
    double x1_max = 9.8;
    double x2_min = 0.0;
    double x2_max = 7.3;
    double vmax1, vmin1;
    double vmax2, vmin2;
    
    //this case using objective function : f(x1,x2) = 19 + x1sin(x1π) + (10 – x2) sin(x2π)
    //where -5,0 ≤ x1 ≤ 9,8; 0,0 ≤ x2 ≤ 7,3
    
    //Method for Init First of Particle's Speed
    public void Init_Speed(int PopSize){
        this.Particle_Speed = new double [PopSize][2];
        
        for (int i=0; i < PopSize; i++){
            for (int j=0; j < 2; j++){
                this.Particle_Speed[i][j] = 0.0;
            }
        }
    }

    //get the value of particle's speed
    public double[][] getParticle_Speed() {
        return Particle_Speed;
    }
    
    //Method for Init of Position's particle
    //Position of particle following this equal : x = xmin + rand[0,1]*(xmax-xmin)
    public void Init_Pos_Particle(int PopSize){
        this.Particle_Position = new double [PopSize][2];
        
        for (int i=0; i < PopSize; i++){
            for (int j=0; j < 2; j++){  
                if (j==0){
                    this.Particle_Position[i][j] = this.x1_min + (Math.random()*(this.x1_max-this.x1_min));
                } else
                    if (j==1){
                        this.Particle_Position[i][j] = this.x2_min + (Math.random()*(this.x2_max-this.x2_min));
                    }
            }
        }
    }
    
    //get the value of particle's position
    public double[][] getParticle_Position() {
        return Particle_Position;
    }
    
    //Calculate the value of fitness from the particle's postion
    public void Calculate_Fitness(double Particle_Postion[][]){
        this.Fitness_Particle = new double [Particle_Position.length]; 
        for (int i=0; i < Particle_Position.length; i++){
         this.Fitness_Particle[i] += 19.0 + (Particle_Position[i][0]*(Math.sin(Particle_Position[i][0]*360)))
                                     + (10.0-Particle_Position[i][1])*(Math.sin(Particle_Position[i][1]*360));

        }
    }
    
    //get the value of fitness particle
    public double[] getFitness_Particle() {
        return Fitness_Particle;
    }
    
    //Init PBest Particle
    public void Init_PBest_Particle(double Particle_Position[][], double fitness_particle[]){
        this.PBest = new double[Particle_Position.length][Particle_Position[0].length];
        this.Fitness_PBest = new double[fitness_particle.length];
        
        for (int i=0; i < Particle_Position.length; i++){
            for (int j=0; j < Particle_Position[0].length; j++){
                this.PBest[i][j] = Particle_Position[i][j];
            }
            this.Fitness_PBest[i] = fitness_particle[i];
        }
    }
    
    //get the pbest init and fitness pbest init
    public double[][] getPBest() {
        return PBest;
    }

    public double[] getFitness_PBest() {
        return Fitness_PBest;
    }
    
    //Init GBest
    public void Init_GBest(double Init_PBest[][], double fitness_Pbest[]){
        this.GBest = new double[Init_PBest[0].length];
        this.Fitness_GBest = fitness_Pbest[0];
        int idx_max = 0;
        
        for (int i=0; i < fitness_Pbest.length; i++){
            if (fitness_Pbest[i] > this.Fitness_GBest){
                this.Fitness_GBest = fitness_Pbest[i];
                idx_max=i;
            }
        }
        
        for (int j=0; j < PBest[0].length;j++){
            this.GBest[j] = Init_PBest[idx_max][j];
        }  
    }
    
    //get the Gbest and fitness of Gbest
    public double[] getGBest() {
        return GBest;
    }
    
    public double getFitness_GBest() {
        return Fitness_GBest;
    }
    
    //calculate limit of velocity each particle
    public void Calculate_Limit(){
        double k = 0.6;
        this.vmax1 = k*((this.x1_max-this.x1_min)/2);
        this.vmin1 = vmax1*-1;
        this.vmax2 = k*((this.x2_max-this.x2_min)/2);
        this.vmin2 = vmax2*-1;
    }

    public double getVmax1() {
        return vmax1;
    }

    public double getVmin1() {
        return vmin1;
    }

    public double getVmax2() {
        return vmax2;
    }

    public double getVmin2() {
        return vmin2;
    }
    
    //Method for calculate update velpcity
    public void Speed_Update(double Particle_Position[][], double PBest[][], double GBest[]
    ,double W, double c1, double c2, double r1, double r2){
      
        double v1max = this.getVmax1();
        double v1min = this.getVmin1();
        double v2max = this.getVmax2();
        double v2min = this.getVmin2();
        Particle_Speed = Update_Speed(Particle_Position, PBest, GBest, W, c1, c2, r1, r2);
        Particle_Speed = Velocity_Clamping(Particle_Speed, v1max, v1min, v2max, v2min);
    }

    private double[][] Update_Speed(double[][] Particle_Position, double[][] PBest, double[] GBest, double W, double c1, double c2, double r1, double r2) {
        for (int i=0; i < Particle_Speed.length; i++){
            for (int j=0; j < Particle_Speed[0].length; j++){
                Particle_Speed[i][j] = (W*Particle_Speed[i][j])+((c1*r1)*(PBest[i][j]-Particle_Position[i][j]))+
                        ((c2*r2)*(GBest[j]-Particle_Position[i][j]));
            }
        }
        
        return Particle_Speed;
    }
    
    private double[][] Velocity_Clamping(double[][] Particle_Speed, double v1max, double v1min, double v2max, double v2min) {
         for (int i=0; i < Particle_Speed.length; i++){
            for (int j=0; j < Particle_Speed[0].length; j++){
                if (j==0){
                    if (Particle_Speed[i][j] > v1max){
                        Particle_Speed[i][j] = v1max;
                    } else
                        if (Particle_Speed[i][j] < v1min){
                         Particle_Speed[i][j] = v1min;   
                        }
                } else
                    if (j==1){
                       if (Particle_Speed[i][j] > v2max){
                        Particle_Speed[i][j] = v2max;
                    } else
                        if (Particle_Speed[i][j] < v2min){
                         Particle_Speed[i][j] = v2min;   
                        } 
                    }
            }
        }
        return Particle_Speed;
    }
    
    //Method for update position
    public void Update_Position(double Particle_Speed[][]){
        this.Particle_Position = updatePosition(Particle_Speed);
        this.Particle_Position = positionlimit(Particle_Position,this.x1_min, this.x1_max, this.x2_min, this.x2_max);
    }

    private double[][] updatePosition(double[][] Particle_Speed) {
        for (int i=0; i < Particle_Position.length; i++){
            for (int j=0; j < Particle_Position[0].length; j++){
                this.Particle_Position[i][j] += Particle_Speed[i][j];
            }
        }   
        return this.Particle_Position;
    }

    private double[][] positionlimit(double[][] Particle_Position, double x1_min, double x1_max, double x2_min, double x2_max) {
         for (int i=0; i < Particle_Position.length; i++){
            for (int j=0; j < Particle_Position[0].length; j++){
                if (j==0){
                    if (Particle_Position[i][j] > x1_max){
                        Particle_Position[i][j] = x1_max;
                    } else
                        if (Particle_Position[i][j] < x1_min){
                            Particle_Position[i][j] = x1_min;
                        }
                } else
                    if (j==1){
                     if (Particle_Position[i][j] > x2_max){
                        Particle_Position[i][j] = x2_max;
                    } else
                        if (Particle_Position[i][j] < x2_min){
                            Particle_Position[i][j] = x2_min;
                        }   
                    }
            }
        }   
        return  Particle_Position;
    }
    
      //Method for Update PBest
    public void Update_PBest_Particle(double PBest[][], double fitness_pbest[], double Particle_Position[][], 
            double fitness_particle[]){
        
        for (int i=0; i < fitness_pbest.length; i++){
            if (fitness_pbest[i] > fitness_particle[i]){
                fitness_pbest[i] = fitness_pbest[i];
                for (int j=0; j < PBest[0].length; j++){
                    this.PBest[i][j] = PBest[i][j];
                }
            } else 
                if (fitness_particle[i] > fitness_pbest[i]){
                    for(int j=0; j < PBest[0].length; j++){
                        fitness_pbest[i] = fitness_particle[i];
                        this.PBest[i][j] = Particle_Position[i][j];
                    }
                }
        }
    }
    
     //Method for Update GBest
    public void Update_GBest(double PBest[][], double fitness_Pbest[]){
        int idx_max = 0;
        
        for (int i=0; i < fitness_Pbest.length; i++){
            if (fitness_Pbest[i] > this.Fitness_GBest){
                this.Fitness_GBest = fitness_Pbest[i];
                idx_max=i;
            }
        }
        
        for (int j=0; j < PBest[0].length;j++){
            this.GBest[j] = PBest[idx_max][j];
        }  
    }
    
}
