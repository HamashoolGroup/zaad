package com.zaad.common.util;

import java.util.Date;

public class ZaadVideoRanking {
	public static double calcPopularity(int likeCount, int dislikeCount, int viewCount, int playlistViewCount, Date creationDate) {
		double likePoint = (double)(likeCount - dislikeCount);
		
		if ( likePoint < 0.0 ) {
			likePoint = 0.0;
		}
		
		double viewPoint = Math.log(viewCount + Math.E);
		double playlistViewPoint = Math.log(playlistViewCount / viewCount + Math.E);
		
		return playlistViewPoint * Math.log(likePoint / viewPoint + Math.E);
	}
	
	public static double calcRecommendarity(int likeCount, int dislikeCount, int viewCount, int commentCount, int playlistViewCount, Date creationDate) {
		double likePoint = (double)likeCount / (dislikeCount +1);
		double viewPoint = Math.log(viewCount + Math.E);
		double timePoint = ((new Date()).getTime() - creationDate.getTime()) / 1000 / 60 / 60 / 24 / 30 / 6.0;
		double commentPoint = Math.log(commentCount + Math.E);
		double playlistViewPoint = Math.log(playlistViewCount / viewCount + Math.E);
		
		if ( timePoint < 1 ) {
			timePoint = 1;
		}
		
		return (likePoint / viewPoint) / timePoint * commentPoint * playlistViewPoint;
	}
	
	public static void main(String[] args) {
//		double popularity_nMFrC3UGtek = calcPopularity(1448, 61, 387751, new Date(1342364400000L));
//		System.out.println("popularity_nMFrC3UGtek=" + popularity_nMFrC3UGtek);
//		double popularity_mnw9x2n72dg = calcPopularity(393, 0, 16726, new Date(1368543600000L));
//		System.out.println("popularity_mnw9x2n72dg=" + popularity_mnw9x2n72dg);
//		double popularity_dQw4w9WgXcQ = calcPopularity(393, 0, 16726, new Date(1368543600000L));
//		System.out.println("popularity_dQw4w9WgXcQ=" + popularity_dQw4w9WgXcQ);
//		double popularity_35syim12_gc = calcPopularity(614, 0, 45520, new Date(1335398400000L));
//		System.out.println("popularity_35syim12=" + popularity_35syim12_gc);
//		double popularity_MDm__atmQjo = calcPopularity(330, 0, 9283, new Date(1449532800000L));
//		System.out.println("popularity_MDm__atmQjo=" + popularity_MDm__atmQjo);
		
		
		double recommendarity_nMFrC3UGtek = calcRecommendarity(0, 0, 99, 0, 68, new Date(1458226800000L));
		System.out.println("recommendarity_nMFrC3UGtek=" + recommendarity_nMFrC3UGtek);
		double recommendarity_mnw9x2n72dg = calcRecommendarity(776, 2, 29334, 0, 106710, new Date(1458226800000L));
		System.out.println("recommendarity_mnw9x2n72dg=" + recommendarity_mnw9x2n72dg);
//		double recommendarity_dQw4w9WgXcQ = calcRecommendarity(1086926, 56423, 189323360, new Date(1256310000000L));
//		System.out.println("recommendarity_dQw4w9WgXcQ=" + recommendarity_dQw4w9WgXcQ);
//		double recommendarity_35syim12_gc = calcRecommendarity(614, 0, 45520, new Date(1335398400000L));
//		System.out.println("recommendarity_35syim12_gc=" + recommendarity_35syim12_gc);
//		double recommendarity_MDm__atmQjo = calcRecommendarity(330, 0, 9283, new Date(1449532800000L));
//		System.out.println("recommendarity_MDm__atmQjo=" + recommendarity_MDm__atmQjo);
		
		
	}
}
